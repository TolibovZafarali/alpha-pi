import { useEffect, useState } from "react";
import { getAuth } from "../../utils/auth";
import { getInvestorProfile, updateInvestorProfile } from "../../services/investorService";
import InvestorProfileForm from "../../components/investor/InvestorProfileForm";

const InvestorDashboard = () => {
    const { id } = getAuth();
    const [profile, setProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [sidebarKey, setSidebarKey] = useState(0);

    // Fetch profile from the backend
    useEffect(() => {
        const fetchProfile = async () => {
            setLoading(true);
            try {
                const res = await getInvestorProfile(id);
                setProfile(res.data);
            } catch (err) {
                console.error(err);
            }
            setLoading(false);
        };
        fetchProfile();
    }, [id]);

    //Check if profile is complete
    const isProfileComplete = profile && profile.contactName && profile.contactEmail && profile.contactPhone && profile.photoUrl;
    
    const handleProfileSave = async (updatedProfile) => {
        try {
            await updateInvestorProfile(id, updatedProfile);
            setProfile(updatedProfile);
            setSidebarKey(prev => prev + 1);
        } catch (err) {
            console.error(err);
        }
    }

    if (loading) return <div className="loading">Loading...</div>

    return (
        <div className="dashboard-container">
            <div className="dashboard-left">
                <InvestorProfileForm
                    profile={profile}
                    onSave={handleProfileSave}
                    isEditable={!isProfileComplete}
                />
            </div>
        </div>
    );
}
 
export default InvestorDashboard;