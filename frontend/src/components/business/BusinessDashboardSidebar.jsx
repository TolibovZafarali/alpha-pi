import { useState } from "react";
import MentorAI from "./MentorAI";
import Messages from "./Messages";
import InterestedInvestors from "./InterestedInvestors";
import PublishToggle from "./PublishToggle";


const BusinessDashboardSidebar = ({ isProfileComplete, isPublished, onPublishChange }) => {
    const [activeSidebar, setActiveSidebar] = useState("menu");

    return (
        <div className="dashboard-sidebar">
            {activeSidebar === "menu" && (
                <div className="sidebar-menu">
                    <button
                        disabled={!isProfileComplete}
                        onClick={() => setActiveSidebar("mentor")}
                        className="sidebar-btn"
                    >
                        Mentor AI
                    </button>
                    <button
                        disabled={!isProfileComplete}
                        onClick={() => setActiveSidebar("messages")}
                        className="sidebar-btn"
                    >
                        Messages
                    </button>
                    <button
                        disabled={!isProfileComplete}
                        onClick={() => setActiveSidebar("investors")}
                        className="sidebar-btn"
                    >
                        Interested Investors
                    </button>
                </div>
            )}

            {activeSidebar === "mentor" && (
                <div className="sidebar-content">
                    <button className="back-btn" onClick={() => setActiveSidebar("menu")}>
                        Back
                    </button>
                    <MentorAI />
                </div>
            )}
            {activeSidebar === "messages" && (
                <div className="sidebar-content">
                    <button className="back-btn" onClick={() => setActiveSidebar("menu")}>
                        Back
                    </button>
                    <Messages />
                </div>
            )}
            {activeSidebar === "investors" && (
                <div className="sidebar-content">
                    <button className="back-btn" onClick={() => setActiveSidebar("menu")}>
                        Back
                    </button>
                    <InterestedInvestors />
                </div>
            )}

            {/* Toggle to publish business profile to investors */}
            <div className="sidebar-toggle-bottom" style={{display: "flex", alignItems: "center", gap: "0.7rem"}}>
                <span style={{fontSize: "1.04rem", fontWeight: 500}}>Post your profile:</span>
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