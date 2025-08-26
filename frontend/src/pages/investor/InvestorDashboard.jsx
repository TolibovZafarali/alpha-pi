import { useEffect, useState } from "react";
import { getMyInvestor, updateMyInvestor } from "../../services/investorService";
import InvestorProfileForm from "../../components/investor/InvestorProfileForm";
import InvestorDashboardSidebar from "../../components/investor/InvestorDashboardSidebar";
import SavedBusinesses from "../../components/investor/SavedBusinesses";
import BrowseBusinesses from "../../components/investor/BrowseBusinesses";
import { saveBusiness, unsaveBusiness } from "../../services/savedService";
import { getAllPublishedBusinesses } from "../../services/businessService";
import "./InvestorDashboard.css";

const InvestorDashboard = () => {
  const [profile, setProfile] = useState(null);
  const [allBusinesses, setAllBusinesses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sidebarKey, setSidebarKey] = useState(0);
  const [activeTab, setActiveTab] = useState("saved");

  // Fetch my investor profile + public businesses
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const [inv, list] = await Promise.all([getMyInvestor(), getAllPublishedBusinesses()]);
        setProfile({ ...(inv.data || {}), savedBusinesses: inv.data?.savedBusinesses || [] });
        setAllBusinesses(list.data || []);
      } catch (err) {
        console.error(err);
      }
      setLoading(false);
    };
    fetchData();
  }, []);
  
  const isProfileComplete =
    profile &&
    profile.contactName &&
    profile.contactEmail &&
    profile.contactPhone &&
    profile.photoUrl;

  const handleProfileSave = async (updatedProfile) => {
    try {
      const { data } = await updateMyInvestor(updatedProfile); // PUT /api/me/investor
      setProfile((prev) => ({
        ...data,
        savedBusinesses: prev?.savedBusinesses || [],
      }));
      setSidebarKey((prev) => prev + 1);
    } catch (err) {
      console.error(err);
    }
  };

  // Save/Unsave use /api/me/saved/{businessId} (no investorId in URL)
  const handleRemove = async (businessId) => {
    try {
      await unsaveBusiness(businessId);
      setProfile((prev) => ({
        ...prev,
        savedBusinesses: (prev?.savedBusinesses || []).filter((b) => b.id !== businessId),
      }));
    } catch (err) {
      console.error("Failed to unsave business: ", err);
    }
  };

  const handleSave = async (businessId) => {
    try {
      await saveBusiness(businessId);
      const saved = allBusinesses.find((b) => b.id === businessId);
      if (!saved) return;
      setProfile((prev) => ({
        ...prev,
        savedBusinesses: [...(prev?.savedBusinesses || []), saved],
      }));
    } catch (err) {
      console.error("Failed to save business: ", err);
    }
  };

  // Remove already saved from the browse section
  const availableBusinesses =
    profile && profile.savedBusinesses
      ? allBusinesses.filter((b) => !profile.savedBusinesses.some((s) => s.id === b.id))
      : allBusinesses;

  if (loading) return <div className="loading">Loading...</div>;

  return (
    <div className="investor-dashboard-container">
      <div className="dashboard-left">
        <InvestorProfileForm
          profile={profile}
          onSave={handleProfileSave}
          isEditable={!isProfileComplete}
        />
      </div>
      <div className="dashboard-right">
        <h2 className="sidebar-header">Invest in Tomorrow's Success</h2>
        <div className="tab-content">
          {activeTab === "saved" && isProfileComplete && (
            <SavedBusinesses
              savedBusinesses={profile.savedBusinesses || []}
              onRemove={handleRemove}
              investorName={profile.contactName}
              investorEmail={profile.contactEmail}
            />
          )}
          {activeTab === "browse" && (
            <BrowseBusinesses
              businesses={availableBusinesses}
              investorProfile={profile}
              onSave={handleSave}
            />
          )}
        </div>
        <div className="sidebar-toggle-bottom">
          <InvestorDashboardSidebar
            key={sidebarKey}
            isProfileComplete={isProfileComplete}
            isEditable={!isProfileComplete}
            onTabChange={setActiveTab}
          />
        </div>
      </div>
    </div>
  );
};

export default InvestorDashboard;
