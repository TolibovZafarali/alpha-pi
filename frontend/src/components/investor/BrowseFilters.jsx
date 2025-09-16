import { useEffect, useMemo, useState } from "react";
import { getMaxInvest, getMinInvest } from "../../utils/getInvestRange";
import industries from "../../data/industries.json"
import "./BrowseFilters.css"
import parseInterests from "../../utils/parseInterests";

const BrowseFilters = ({ investorProfile, onFilterChange }) => {
    const [activeMainFilter, setActiveMainFilter] = useState("industry");
    const [investmentRangeEnabled, setInvestmentRangeEnabled] = useState(false);
    const [selectedIndustry, setSelectedIndustry] = useState("All");

    // Parse investor interests, they are stored as comma-separated string
    const interestsSource = investorProfile?.interests ?? "";
    const parsedInterests = useMemo(
        () => parseInterests(interestsSource),
        [interestsSource]
    );

    const investmentRange = investorProfile?.investmentRange;
    const minInvest = useMemo(() => getMinInvest(investmentRange), [investmentRange]);
    const maxInvest = useMemo(() => getMaxInvest(investmentRange), [investmentRange]);

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
    }, [
        activeMainFilter,
        selectedIndustry,
        investmentRangeEnabled,
        maxInvest,
        minInvest,
        onFilterChange,
        parsedInterests,
    ]);
    
    return (
        <div className="browse-filters">
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