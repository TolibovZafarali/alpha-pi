import InterestedInvestors from "./InterestedInvestors";
import PublishToggle from "./PublishToggle";
import "./BusinessDashboardSidebar.css"


const BusinessDashboardSidebar = ({ isProfileComplete, isPublished, onPublishChange, interestedInvestors }) => {
    return (
        <div className="dashboard-sidebar">
            <h2 className="sidebar-header">Interested Investors</h2>

            <InterestedInvestors interestedInvestors={interestedInvestors} />

            <div className="business-sidebar-toggle-bottom">
                <span className="publish-label">Post your profile:</span>
                <PublishToggle
                    checked={isPublished}
                    disabled={!isProfileComplete}
                    onChange={onPublishChange}
                />
            </div>
        </div>
    )
}
 
export default BusinessDashboardSidebar;