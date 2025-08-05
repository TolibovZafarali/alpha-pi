import { useState } from "react";
import emailjs from "@emailjs/browser"
import formatPhoneNumber from "../../utils/getPhoneNumber";
import "./SavedBusinesses.css"

const SavedBusinesses = ({ savedBusinesses, onRemove, investorName, investorEmail }) => {
    const [expandedModeId, setExpandedMoreId] = useState(null);
    const [expandedContactId, setExpandedContactId] = useState(null);
    const [message, setMessage] = useState("");
    const [statusMessages, setStatusMessages] = useState({});

    const handleToggleMore = (id) => {
        setExpandedMoreId(expandedModeId === id ? null : id);
        setExpandedContactId(null);
        setMessage(message);
    };

    const handleToggleContact = (id) => {
        setExpandedContactId(expandedContactId === id ? null : id);
        setExpandedMoreId(null);
        setMessage(message);
    }

    const handleSend = (business) => {
        const templateParams = {
            to_name: business.contactName,
            to_email: business.contactEmail,
            from_name: investorName,
            from_email: investorEmail,
            message: message,
        }
    
        emailjs
            .send("service_n1xxg5b", "template_ivn8aki", templateParams, "-efmrM26rTZPXQY6D")
            .then(() => {
                setStatusMessages(prev => ({
                    ...prev,
                    [business.id]: "Message sent successfully!"
                }))
                setExpandedContactId(null);
                setMessage("");
            })
            .catch((err) => {
                console.error("Email error", err);
                setStatusMessages(prev => ({
                    ...prev,
                    [business.id]: "Failed to send message. Please try again."
                }))
            });
    };

    if (!savedBusinesses || savedBusinesses.length === 0) {
        return (
            <div className="saved-businesses">
                <p>No saved businesses yet.</p>
            </div>
        )
    }
    return (
        <div>
            {savedBusinesses.map((business) => {
                const isMoreOpen = expandedModeId === business.id;
                const isContactOpen = expandedContactId === business.id;

                return (
                    <div key={business.id} className="business-card">
                        <div className="business-card-top">
                            {/* Logo */}
                            <div className="business-logo">
                                <img src={business.logoUrl} alt="logo" />
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
                                <button onClick={() => handleToggleContact(business.id)}>Contact</button>
                                <button onClick={() => onRemove(business.id)}>Remove</button>
                                <button onClick={() => handleToggleMore(business.id)}>
                                    {isMoreOpen ? "Close" : "More"}
                                </button>
                            </div>
                        </div>

                        {/* Expanded Contact Form */}
                        {isContactOpen && (
                            <div className="business-contact-form">
                                <textarea
                                    placeholder={`Write your message to ${business.contactName}`}
                                    value={message}
                                    onChange={(e) => setMessage(e.target.value)}
                                    rows={4}
                                />
                                <button onClick={() => (handleSend(business))}>Send</button>
                                {statusMessages[business.id] && (
                                    <p className="status-message">{statusMessages[business.id]}</p>
                                )}
                            </div>
                        )}

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