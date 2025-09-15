import { useEffect, useState } from "react";
import InvestorProfileBlueprint from "../../models/InvestorProfileBlueprint"
import { getMaxInvest, getMinInvest } from "../../utils/getInvestRange";
import formatPhoneNumber from "../../utils/getPhoneNumber";
import formatCurrency from "../../utils/formatCurrency";
import industries from "../../data/industries.json"
import "./InvestorProfileForm.css"
import states from "../../data/states.json"
import isInvestorProfileComplete from "../../utils/isInvestorProfileComplete";

const InvestorProfileForm = ({ profile, onSave, isEditable: parentEditable }) => {
    //State for all fields required by blueprint class
    const [contactName, setContactName] = useState("");
    const [contactEmail, setContactEmail] = useState("");
    const [contactPhone, setContactPhone] = useState("");
    const [photoUrl, setPhotoUrl] = useState("");
    const [state, setState] = useState("");
    const [interests, setInterests] = useState("");
    const [editMode, setEditMode] = useState("");

    const [selectedInterests, setSelectedInterests] = useState([]);

    const [minInvest, setMinInvest] = useState("");
    const [maxInvest, setMaxInvest] = useState("");

    // Fetch data from the backend
    useEffect(() => {
        if (!profile) return;
        
        setContactName(profile.contactName || "");
        setContactEmail(profile.contactEmail || "");
        setContactPhone(profile.contactPhone || "");
        setPhotoUrl(profile.photoUrl || "");
        setState(profile.state || "");
        setInterests(profile.interests || "");

        const range = profile.investmentRange || "";
        const minInvestment = getMinInvest(range);
        const maxInvestment = getMaxInvest(range);

        setMinInvest(minInvestment != null ? minInvestment : "");
        setMaxInvest(maxInvestment != null ? maxInvestment : "");

        const interestList = profile.interests ? profile.interests.split(",").map((s) => s.trim()) : [];
        setSelectedInterests(interestList);

        setEditMode(parentEditable);
    }, [profile, parentEditable]);
    
    //Disable save button
    const allFieldsFilled = 
        contactName &&
        contactEmail &&
        contactPhone &&
        photoUrl &&
        state &&
        minInvest &&
        maxInvest &&
        selectedInterests.length >= 2;
    
    const handleInterestChange = (interest) => {
        let updated;
        if (selectedInterests.includes(interest)) {
            updated = selectedInterests.filter((i) => i !== interest);
        } else {
            if (selectedInterests.length >= 5) return;
            updated = [...selectedInterests, interest];
        }
        setSelectedInterests(updated);
        setInterests(updated.join(", "));
    }


    // Handle save button
    const handleSave = (e) => {
        e.preventDefault();
        const updatedProfile = new InvestorProfileBlueprint(
            contactName,
            contactEmail,
            contactPhone,
            photoUrl,
            state,
            `${minInvest}-${maxInvest}`,
            interests
        );
        onSave(updatedProfile);
        setEditMode(false);
    }

    // Show profile preview, if profile is not in edit mode and is complete
    if (!editMode && isInvestorProfileComplete(profile)) {
        return (
            <div className="profile-preview-container">
                <div className="profile-fields-left">
                    <h3 className="contact-header">CONTACT INFO</h3>

                    <div className="profile-info">
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

                        <hr />

                        <div className="info-row">
                            <div className="info-label">State</div>
                            <div className="info-value"><strong>{profile.state}</strong></div>
                        </div>

                        <div className="info-row">
                            <div className="info-label">Investment Range</div>
                            <div className="info-value">{`${formatCurrency(minInvest)} - ${formatCurrency(maxInvest)}`}</div>
                        </div>
                        <div className="info-row">
                            <div className="info-label">Interests</div>
                            <div className="info-value interest-tags">
                                {profile.interests
                                    .split(",")
                                    .map((interest) => interest.trim())
                                    .map((interest, i) => (
                                        <span key={i} className="interest-pill">{interest}</span>
                                    ))}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="profile-photo-topright">
                    <img
                        src={profile.photoUrl}
                        alt="Profile photo"
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
                    <div className="contact-info-head">Contact Info</div>
                    
                    <div className="row-pair">
                        <div className="input-wrapper">
                            <input
                                type="text"
                                value={contactName}
                                onChange={(e) => setContactName(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Name</label>
                        </div>
                        <div className="input-wrapper">
                            <input
                                type="email"
                                value={contactEmail}
                                onChange={(e) => setContactEmail(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Email</label>
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
                            <label className="floating-label">Phone</label>
                        </div>
                    </div>

                    <div className="row-pair">
                        <div className="input-wrapper">
                            <select
                                value={state}
                                onChange={(e) => setState(e.target.value)}
                                required
                                placeholder=" "
                            >
                                <option value="">Select State</option>
                                {states.map((st, idx) => (
                                    <option key={idx} value={st}>{st}</option>
                                ))}
                            </select>
                            <label className="floating-label">State</label>
                        </div>
                        
                        <div className="input-wrapper">
                            <input
                                type="text"
                                value={photoUrl}
                                onChange={(e) => setPhotoUrl(e.target.value)}
                                required
                                placeholder=" "
                            />
                            <label className="floating-label">Photo URL</label>
                        </div>
                    </div>
                    <div className="investment-range">Investment Range</div>
                    
                    <div className="row-pair">
                        <div className="input-wrapper">
                            <input
                                type="number"
                                value={minInvest}
                                onChange={(e) => setMinInvest(e.target.value)}
                                required
                                min={0}
                                max={maxInvest}
                                placeholder=" "
                            />
                            <label className="floating-label">Minimum</label>
                        </div>
                        <div className="input-wrapper">
                            <input
                                type="number"
                                value={maxInvest}
                                onChange={(e) => setMaxInvest(e.target.value)}
                                required
                                min={minInvest}
                                placeholder=" "
                            />
                            <label className="floating-label">Maximum</label>
                        </div>
                    </div>
                    <div className="input-wrapper">
                        <label className="checkbox-group-label">Select Interests</label>
                        <div className="interests-checkboxes">
                            {industries.map((industry, i) => (
                                <label
                                    key={i}
                                    className={`checkbox-label ${
                                        selectedInterests.includes(industry) ? "checked" : ""
                                    }`}
                                >
                                    <input
                                        type="checkbox"
                                        value={industry}
                                        checked={selectedInterests.includes(industry)}
                                        onChange={() => handleInterestChange(industry)}
                                        disabled={
                                            selectedInterests.length >= 5 &&
                                            !selectedInterests.includes(industry)
                                        }
                                    />
                                    {industry}
                                </label>
                            ))}
                        </div>
                    </div>

                    <div className="save-btn-bottomright">
                        <button type="submit" disabled={!allFieldsFilled}>Save</button>
                    </div>
                </div>
            </form>
        </div>
    );
}
 
export default InvestorProfileForm;