import { useState } from "react";

const InvestorDashboardSidebar = ({ isProfileComplete, isEditable, onTabChange }) => {
    const [activeTab, setActiveTab] = useState("saved");
    const isDisabled = !isProfileComplete || isEditable;

    const handleClick = (tab) => {
        if (isDisabled) return;
        setActiveTab(tab);
        onTabChange(tab);
    };
    
    return (
        <div className="dashboard-sidebar">
            <h2 className="sidebar-header">Invest in Tomorrow's Success</h2>

            <div className="tab-switcher">
                <button
                    className={`tab-button ${activeTab === "saved" ? "active" : ""}
                    ${isDisabled ? "disabled" : ""}
                    `}
                    onClick={() => handleClick("saved")}
                >
                    Saved
                </button>
                <button
                    className={`tab-button ${activeTab === "browse" ? "active" : ""}
                    ${isDisabled ? "disabled" : ""}
                    `}
                    onClick={() => handleClick("browse")}
                >
                    Browse
                </button>
            </div>

            {isDisabled && (
                <p className="tab-warning">
                    {isEditable
                        ? "Finish editing your profile to continue."
                        : "Complete your profile to access this section."}
                </p>
            )}
        </div>
    );
}
 
export default InvestorDashboardSidebar;