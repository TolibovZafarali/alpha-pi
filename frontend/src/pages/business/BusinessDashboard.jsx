import { useEffect, useState } from "react";
import {
  getMyBusiness,
  updateMyBusiness,
  setPublishMyBusiness,
  getInterestedInMyBusiness,
} from "../../services/businessService";
import BusinessProfileForm from "../../components/business/BusinessProfileForm";
import BusinessDashboardSidebar from "../../components/business/BusinessDashboardSidebar";
import "./BusinessDashboard.css";

const BusinessDashboard = () => {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [sidebarKey, setSidebarKey] = useState(0);

  // Fetch my business profile + interested investors
  useEffect(() => {
    const fetchProfile = async () => {
      setLoading(true);
      try {
        const [me, interested] = await Promise.all([
          getMyBusiness(),                 // /api/me/business
          getInterestedInMyBusiness(),     // /api/me/business/interested
        ]);
        setProfile({ ...me.data, interestedInvestors: interested.data || [] });
      } catch (err) {
        console.error(err);
      }
      setLoading(false);
    };
    fetchProfile();
  }, []);

  // Profile completeness + publish
  const isProfileComplete =
    profile && profile.businessName && profile.industry && profile.description;
  const isPublished = profile?.published ?? false;

  // Update profile (no id in URL anymore)
  const handleProfileSave = async (updatedProfile) => {
    try {
      const { data } = await updateMyBusiness(updatedProfile); // PUT /api/me/business
      setProfile((prev) => ({
        ...data,
        // keep the interested list we already fetched
        interestedInvestors: prev?.interestedInvestors || [],
      }));
      setSidebarKey((prevKey) => prevKey + 1); // force sidebar re-render if needed
    } catch (err) {
      console.error(err);
    }
  };

  // Toggle publish (dedicated endpoint)
  const handlePublishChange = async (newValue) => {
    try {
      await setPublishMyBusiness(newValue); // PATCH /api/me/business/publish?value=...
      setProfile((p) => ({ ...p, published: newValue }));
    } catch (err) {
      console.error(err);
    }
  };

  if (loading) return <div className="loading">Loading...</div>;

  return (
    <div className="business-dashboard-container">
      <div className="dashboard-left">
        <BusinessProfileForm
          profile={profile}
          onSave={handleProfileSave}
          isEditable={!isProfileComplete}
        />
      </div>
      <div className="dashboard-right">
        <BusinessDashboardSidebar
          key={sidebarKey}
          isProfileComplete={isProfileComplete}
          isPublished={isPublished}
          onPublishChange={handlePublishChange}
          interestedInvestors={profile.interestedInvestors || []}
        />
      </div>
    </div>
  );
};

export default BusinessDashboard;
