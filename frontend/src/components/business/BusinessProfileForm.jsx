import { useEffect, useState } from "react";
import BusinessProfileBlueprint from "../../models/BusinessProfileBlueprint";

import industries from "../../data/industries.json"
import formatCurrency from "../../utils/formatCurrency";
import "./BusinessProfileForm.css"
import formatPhoneNumber from "../../utils/getPhoneNumber";
import isProfileComplete from "../../utils/isProfileComplete";

const BusinessProfileForm = ({ profile, onSave, isEditable: parentEditable }) => {
    // State for all fields required by blueprint class
    const [businessName, setBusinessName] = useState("");
    const [industry, setIndustry] = useState("");
    const [description, setDescription] = useState("");
    const [logoUrl, setLogoUrl] = useState("");
    const [fundingGoal, setFundingGoal] = useState("");
    const [currentRevenue, setCurrentRevenue] = useState("");
    const [foundedDate, setFoundedDate] = useState("");
    const [contactName, setContactName] = useState("");
    const [contactEmail, setContactEmail] = useState("");
    const [contactPhone, setContactPhone] = useState("");
    const [editMode, setEditMode] = useState("");

    // Fetch the data from the backend
    useEffect(() => {
        if (profile) {
            setBusinessName(profile.businessName || "");
            setIndustry(profile.industry || "");
            setDescription(profile.description || "");
            setLogoUrl(profile.logoUrl || "");
            setFundingGoal(profile.fundingGoal || "");
            setCurrentRevenue(profile.currentRevenue || "");
            setFoundedDate(profile.foundedDate || "");
            setContactName(profile.contactName || "");
            setContactEmail(profile.contactEmail || "");
            setContactPhone(profile.contactPhone || "");
        }
        setEditMode(parentEditable);
    }, [profile, parentEditable]);

    // Disable save button
    const allFieldsFilled = 
        businessName &&
        industry &&
        description &&
        logoUrl &&
        fundingGoal &&
        currentRevenue &&
        foundedDate &&
        contactName &&
        contactEmail &&
        contactPhone;
    
    // Handle Save button
    const handleSave = (e) => {
        e.preventDefault();
        const updatedProfile = new BusinessProfileBlueprint(
            businessName,
            industry,
            description,
            logoUrl,
            Number(fundingGoal),
            Number(currentRevenue),
            foundedDate,
            contactName,
            contactEmail,
            contactPhone
        );
        onSave(updatedProfile);
        setEditMode(false);
    }

    // SHOW profile preview, if profile is not in edit mode and profile is complete
    if (!editMode && isProfileComplete(profile)) {
        return (
            <div className="profile-preview-container">
                <div className="profile-fields-left">
                    <h2 className="business-name">{profile.businessName}</h2>

                    <div className="profile-info">
                        <div className="info-row">
                            <div className="info-label">Industry</div>
                            <div className="info-value"><strong>{profile.industry}</strong></div>
                        </div>
                        <div className="info-row desc">
                            <div className="info-label">Description</div>
                            <div className="info-value"><strong>{profile.description}</strong></div>
                        </div>

                        <div className="info-row-pair">
                            <div className="info-block">
                                <div className="info-label">Funding Goal</div>
                                <div className="info-value"><strong>{formatCurrency(profile.fundingGoal)}</strong></div>
                            </div>
                            <div className="info-block">
                                <div className="info-label">Current Revenue</div>
                                <div className="info-value"><strong>{formatCurrency(profile.currentRevenue)}</strong></div>
                            </div>
                            <div className="info-row">
                                <div className="info-label">Founded Date</div>
                                <div className="info-value"><strong>{profile.foundedDate}</strong></div>
                            </div>
                        </div>

                        <hr />

                        <div className="contact-header">Contact Info</div>
                        <div className="info-row">
                            <div className="info-label">Name</div>
                            <div className="info-value"><strong>{profile.contactName}</strong></div>
                        </div>
                        <div className="info-row">
                            <div className="info-label">Email</div>
                            <div className="info-value"><strong>{profile.contactEmail}</strong></div>
                        </div>
                        <div className="info-row">
                            <div className="info-label">Phone</div>
                            <div className="info-value"><strong>{formatPhoneNumber(profile.contactPhone)}</strong></div>
                        </div>
                    </div>
                </div>

                <div className="profile-logo-topright">
                    <img
                        src={profile.logoUrl}
                        alt="Business Logo"
                        onError={(e) => (e.target.src = "/not-found.svg")}
                    />
                </div>

                <div className="profile-edit-btn-bottomright">
                    <button onClick={() => setEditMode(true)}>Edit</button>
                </div>
            </div>
        )
    }

    return (
        <div className="profile-form-container">
            <form className="profile-form" onSubmit={handleSave}>
                <div className="fields-left">
                    <div className="input-wrapper">
                        <input 
                            type="text"
                            value={businessName}
                            onChange={(e) => setBusinessName(e.target.value)}
                            required
                            placeholder=" "
                        />
                        <label className="floating-label">Business Name</label>
                    </div>
                    <div className="row-pair">
                    <div className="input-wrapper">
                        <select
                            value={industry}
                            onChange={(e) => setIndustry(e.target.value)}
                            required
                            placeholder=" "
                        >
                            <option value="">Select industry</option>
                            {industries.map((ind, idx) => (
                                <option key={idx} value={ind}>{ind}</option>
                            ))}
                        </select>
                        <label className="floating-label">Industry</label>
                    </div>
                        <div className="input-wrapper">
                            <input
                                type="text"
                                value={logoUrl}
                                onChange={(e) => setLogoUrl(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Logo URL</label>
                        </div>
                    </div>
                    <div className="input-wrapper">
                        <textarea 
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                            placeholder=" "
                            maxLength={140}
                        />
                        <label className="floating-label">Description</label>
                        <p className="char-counter">{description.length}140</p>
                    </div>
                    <div className="row-triple">
                        <div className="input-wrapper">
                            <input 
                                type="number"
                                value={fundingGoal}
                                onChange={(e) => setFundingGoal(e.target.value)}
                                required
                                min="0"
                                placeholder=" "
                            />
                            <label className="floating-label">Funding Goal</label>
                        </div>
                        <div className="input-wrapper">
                            <input 
                                type="number"
                                value={currentRevenue}
                                onChange={(e) => setCurrentRevenue(e.target.value)}
                                required
                                min="0"
                                placeholder=" "
                            />
                            <label className="floating-label">Current Revenue</label>
                        </div>
                        <div className="input-wrapper">
                            <input
                                type="date"
                                value={foundedDate}
                                onChange={(e) => setFoundedDate(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Founded Date</label>
                        </div>
                    </div>
                        <div className="input-wrapper">
                            <input
                                type="text"
                                value={contactName}
                                onChange={(e) => setContactName(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Contact Name</label>
                        </div>
                        <div className="input-wrapper">
                            <input
                                type="email"
                                value={contactEmail}
                                onChange={(e) => setContactEmail(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Contact Email</label>
                        </div>
                        <div className="input-wrapper">
                            <input
                                type="tel"
                                value={contactPhone}
                                onChange={(e) => {
                                    const digitsOnly = e.target.value.replace(/\D/g, "");
                                    setContactPhone(digitsOnly);
                                }}
                                required
                                placeholder=" "
                                inputMode="numeric"
                                pattern="[0-9]*"
                                maxLength={10}
                                minLength={10}
                            />
                            <label className="floating-label">Contact Phone</label>
                        </div>

                    <div className="save-btn-bottomright">
                        <button type="submit" disabled={!allFieldsFilled}>Save</button>
                    </div>
                </div>
            </form>
        </div>
    );
}
 
export default BusinessProfileForm;