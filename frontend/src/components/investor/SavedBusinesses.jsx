import { useEffect, useMemo, useState } from "react";
import formatPhoneNumber from "../../utils/getPhoneNumber";
import InvestorChatPanel from "./InvestorChatPanel";
import "./SavedBusinesses.css"

const createInitialMessages = (business) => [
    {
        id: `intro-${business.id}`,
        from: "business",
        text: `Hi ${business.contactName} here from ${business.businessName}. Thanks for saving our profile!`,
        createdAt: new Date(),
    },
];

const SavedBusinesses = ({ savedBusinesses, onRemove }) => {
    const [expandedModeId, setExpandedMoreId] = useState(null);
    const [activeChatBusinessId, setActiveChatBusinessId] = useState(null);
    const [messagesByBusiness, setMessagesByBusiness] = useState({});

    useEffect(() => {
        if (activeChatBusinessId && !savedBusinesses.some((b) => b.id === activeChatBusinessId)) {
            setActiveChatBusinessId(null);
        }
    }, [activeChatBusinessId, savedBusinesses]);

    const activeBusiness = useMemo(
        () => savedBusinesses.find((business) => business.id === activeChatBusinessId) || null,
        [activeChatBusinessId, savedBusinesses]
    );

    const ensureConversation = (business) => {
        setMessagesByBusiness((prev) => {
            if (prev[business.id]) return prev;
            return {
                ...prev,
                [business.id]: createInitialMessages(business),
            };
        });
    };

    const handleToggleMore = (id) => {
        setExpandedMoreId(expandedModeId === id ? null : id);
    };

    const handleOpenChat = (business) => {
        ensureConversation(business);
        setActiveChatBusinessId(business.id);
    };

    const handleSendMessage = (business, text) => {
        setMessagesByBusiness((prev) => ({
            ...prev,
            [business.id]: [
                ...(prev[business.id] || []),
                {
                    id: `investor-${business.id}-${Date.now()}`,
                    from: "investor",
                    text,
                    createdAt: new Date(),
                },
            ],
        }));
    };

    const handleCloseChat = () => {
        setActiveChatBusinessId(null);
    };

    if (!savedBusinesses || savedBusinesses.length === 0) {
        return (
            <div className="saved-businesses">
                <p>No saved businesses yet.</p>
            </div>
        )
    }

    if (activeBusiness) {
        return (
            <div className="saved-businesses chat-active">
                <InvestorChatPanel
                    business={activeBusiness}
                    messages={messagesByBusiness[activeBusiness.id] || []}
                    onSendMessage={(text) => handleSendMessage(activeBusiness, text)}
                    onBack={handleCloseChat}
                />
            </div>
        );
    }

    return (
        <div className="saved-businesses">
            {savedBusinesses.map((business) => {
                const isMoreOpen = expandedModeId === business.id;

                return (
                    <div key={business.id} className="business-card">
                        <div className="business-card-top">
                            {/* Logo */}
                            <div className="business-logo">
                                <img
                                    src={business.logoUrl || "/LOGO.svg"}
                                    alt="logo"
                                    onError={(e) => {
                                        if (e.target.src.endsWith("/LOGO.svg")) return;
                                        e.target.src = "/LOGO.svg";
                                    }}
                                />
                            </div>

                            {/* Name + Industry */}
                            <div className="business-name-industry">
                                <div className="business-name">{business.businessName}</div>
                                <div className="business-industry">{business.industry}</div>
                            </div>

                            {/* Contact Info */}
                            <div className="business-contact-info">
                                <div>{business.contactName}</div>
                                <div>{business.contactEmail}</div>
                                <div>{formatPhoneNumber(business.contactPhone)}</div>
                            </div>

                            {/* Action Buttons */}
                            <div className="business-action-buttons">
                                <button onClick={() => handleOpenChat(business)}>Message</button>
                                <button onClick={() => onRemove(business.id)}>Remove</button>
                                <button onClick={() => handleToggleMore(business.id)}>
                                    {isMoreOpen ? "Close" : "More"}
                                </button>
                            </div>
                        </div>

                        {/* Expanded More Info */}
                        {isMoreOpen && (
                            <div className="business-more-info">
                                <p><strong>Funding Goal:</strong> ${business.fundingGoal?.toLocaleString()}</p>
                                <p><strong>Current Revenue:</strong> ${business.currentRevenue?.toLocaleString()}</p>
                                <p><strong>Founded:</strong> {business.foundedDate}</p>
                                <p><strong>Description:</strong> {business.description}</p>
                            </div>
                        )}
                    </div>
                );
            })}
        </div>
    );
}
 
export default SavedBusinesses;