import { useState, useEffect } from "react";
import "./InvestorDashboardSidebar.css";

const InvestorDashboardSidebar = ({ isProfileComplete, isEditable, onTabChange }) => {
    const [activeTab, setActiveTab] = useState("browse");
    const isDisabled = !isProfileComplete || isEditable;

    const handleClick = (tab) => {
        if (isDisabled) return;
        setActiveTab(tab);
        onTabChange(tab);
    };

    // Ensure toggle is in sync if props reset the view
    useEffect(() => {
        if (isDisabled) setActiveTab("browse");
    }, [isDisabled]);

    return (
        <div className="dashboard-sidebar">
            

            <div className={`tab-toggle-switch ${isDisabled ? "disabled" : ""}`}>
                <div
                    className={`toggle-option ${activeTab === "browse" ? "active" : ""}`}
                    onClick={() => handleClick("browse")}
                >
                    Browse
                </div>
                <div
                    className={`toggle-option ${activeTab === "saved" ? "active" : ""}`}
                    onClick={() => handleClick("saved")}
                >
                    Saved
                </div>
                <div className={`toggle-slider ${activeTab}`}></div>
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
};

export default InvestorDashboardSidebar;
