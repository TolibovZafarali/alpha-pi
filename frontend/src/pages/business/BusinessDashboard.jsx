import { useEffect, useState } from "react";
import { getAuth } from "../../utils/auth";
import { getBusinessProfile, updateBusinessProfile } from "../../services/businessService";
import BusinessProfileForm from "../../components/business/BusinessProfileForm";
import BusinessDashboardSidebar from "../../components/business/BusinessDashboardSidebar";
import "./BusinessDashboard.css"

const BusinessDashboard = () => {
    const { id } = getAuth();
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [sidebarKey, setSidebarKey] = useState(0);

    // Fetch profile from the backend
    useEffect(() => {
        const fetchProfile = async () => {
            setLoading(true);
            try {
                const res = await getBusinessProfile(id);
                setProfile(res.data);
            } catch (err) {
                console.error(err);
            }
            setLoading(false);
        };
        fetchProfile();
    }, [id]);

    // Check if profile is complete
    const isProfileComplete = profile && profile.businessName && profile.industry && profile.description;
    const isPublished = profile?.published ?? false;

    // Create || Update profile
    const handleProfileSave = async (updatedProfile) => {
        try {
            await updateBusinessProfile(id, updatedProfile);
            setProfile(updatedProfile);
            setSidebarKey(prev => prev + 1); // this is to force sidebar to re-render
        } catch (err) {
            console.error(err);
        }
    };

    // Handle publish toggle
    const handlePublishChange = async (newValue) => {
        try {
            const updated = { ...profile, published: newValue };
            await updateBusinessProfile(id, updated);
            setProfile(updated);
        } catch (err) {
            console.error(err);
        }
    };

    if (loading) return <div className="loading">Loading...</div>
    
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
}
 
export default BusinessDashboard;