import { useEffect, useState } from "react";
import { getMaxInvest, getMinInvest } from "../../utils/getInvestRange";
import industries from "../../data/industries.json"

const BrowseFilters = ({ investorProfile, onFilterChange }) => {
    const [activeMainFilter, setActiveMainFilter] = useState("industry");
    const [investmentRangeEnabled, setInvestmentRangeEnabled] = useState(false);
    const [selectedIndustry, setSelectedIndustry] = useState("All");

    // Parse investor interests, they are stored as comma-seperated string
    const parsedInterests = investorProfile?.interests
        ? investorProfile.interests.split(",").map(i => i.trim())
        : [];

    const minInvest = investorProfile ? getMinInvest(investorProfile.investmentRange) : null;
    const maxInvest = investorProfile ? getMaxInvest(investorProfile.investmentRange) : null;

    // handle filted changes and notify parent component
    useEffect(() => {
        onFilterChange({
            activeMainFilter,
            selectedIndustry,
            useInvestmentRange: investmentRangeEnabled,
            interests: parsedInterests,
            minInvest,
            maxInvest
        });
    }, [activeMainFilter, selectedIndustry, investmentRangeEnabled]);
    
    return (
        <div className="browse-filters">
            <h3>Filter by:</h3>
            <div className="filter-options-row">
                <label>
                    <input
                        type="checkbox"
                        checked={activeMainFilter === "industry"}
                        onChange={() => setActiveMainFilter("industry")}
                    />
                    Industry
                </label>

                <label>
                    <input
                        type="checkbox"
                        checked={activeMainFilter === "interests"}
                        onChange={() => setActiveMainFilter("interests")}
                    />
                    Interests
                </label>

                <label>
                    <input
                        type="checkbox"
                        checked={investmentRangeEnabled}
                        onChange={() => setInvestmentRangeEnabled(!investmentRangeEnabled)}
                    />
                    Investment Range
                </label>
            </div>

            {/* Industry dropdown only if industry is selected */}
            {activeMainFilter === "industry" && (
                <div className="industry-dropdown">
                    <label>Select Industry:</label>
                    <select value={selectedIndustry} onChange={(e) => setSelectedIndustry(e.target.value)}>
                        <option value="All">All</option>
                        {industries.map((industry) => (
                            <option key={industry} value={industry}>
                                {industry}
                            </option>
                        ))}
                    </select>
                </div>
            )}
        </div>
    );
}
 
export default BrowseFilters;