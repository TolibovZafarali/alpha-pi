import { useMemo, useState } from "react";
import InterestedInvestors from "./InterestedInvestors";
import PublishToggle from "./PublishToggle";
import InvestorChatPanel from "./InvestorChatPanel"
import "./BusinessDashboardSidebar.css"


const BusinessDashboardSidebar = ({ 
    isProfileComplete, 
    isPublished, 
    onPublishChange, 
    interestedInvestors = []
}) => {
    const [activeInvestorId, setActiveInvestorId] = useState(null);

    const activeInvestor = useMemo(() => {
        if (!activeInvestorId) return null;

        return (
            interestedInvestors.find((investor) => {
                if (!investor) return false;
                if (investor.id && investor.id === activeInvestorId) return true;
                if (investor.contactEmail && investor.contactEmail === activeInvestorId) return true;
                if (investor.contactName && investor.contactName === activeInvestorId) return true;
                return false;
            }) || null
        );
    }, [activeInvestorId, interestedInvestors]);

    const handleOpenChat = (investor) => {
        const identifier = investor.id || investor.contactEmail || investor.contactName;
        setActiveInvestorId(identifier || null);
      };
    
      const handleCloseChat = () => {
        setActiveInvestorId(null);
      };
    
      const sidebarClassName = `dashboard-sidebar${activeInvestor ? " chat-active" : ""}`;
    
      return (
        <div className={sidebarClassName}>
          {activeInvestor ? (
            <InvestorChatPanel investor={activeInvestor} onBack={handleCloseChat} />
            ) : (
            <>
              <h2 className="sidebar-header">Interested Investors</h2>
    
              <InterestedInvestors interestedInvestors={interestedInvestors} onMessage={handleOpenChat} />
    
              <div className="business-sidebar-toggle-bottom">
                <span className="publish-label">Post your profile:</span>
                <PublishToggle
                  checked={isPublished}
                  disabled={!isProfileComplete}
                  onChange={onPublishChange}
                />
              </div>
            </>
          )}
        </div>
    );
};
    
export default BusinessDashboardSidebar;