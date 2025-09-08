import { useEffect, useState } from "react";
import BrowseFilters from "./BrowseFilters";
import formatCurrency from "../../utils/formatCurrency";
import "./BrowseBusinesses.css"

const BrowseBusinesses = ({ businesses, investorProfile, onSave }) => {
    const [filterBusinesses, setFilterBusinesses] = useState([]);
    const [expandedId, setExpandedId] = useState(null);

    const handleToggleMore = (id) => {
        setExpandedId(expandedId === id ? null : id);
    };

    const handleFilterChange = ({
        activeMainFilter,
        selectedIndustry,
        useInvestmentRange,
        interests,
        minInvest,
        maxInvest,
    }) => {
        let result = [...businesses];

        // Filter by industry
        if (activeMainFilter === "industry" && selectedIndustry!== "All") {
            result = result.filter((b) => b.industry === selectedIndustry);
        }

        // Filter by investor's interests
        if (activeMainFilter === "interests") {
            result = result.filter((b) => interests.includes(b.industry));
        }

        // Filter by investment range, doesn't matter if any of the above is checked
        if (useInvestmentRange && minInvest !== null && maxInvest !== null) {
            result = result.filter((b) => {
                const goal = Number(b.fundingGoal);
                return goal >= minInvest && goal <= maxInvest;
            });
        }

        setFilterBusinesses(result);
    }
    
    useEffect(() => {
        setFilterBusinesses(businesses);
    }, [businesses]);

    return (
        <div className="browse-businesses">
            <BrowseFilters investorProfile={investorProfile} onFilterChange={handleFilterChange} />
            <div className="browse-businesses-list">
            {filterBusinesses.length === 0 ? (
                <p>No businesses match your filters.</p>
            ) : (
                filterBusinesses.map((business) => {
                    const isExpanded = expandedId === business.id;

                    return (
                        <div key={business.id} className="browse-card">
                            <div className="browse-card-top">
                                <div className="browse-logo">
                                    <img src={business.logoUrl} alt="logo" onError={(e) => (e.target.src = "/LOGO.svg")}/>
                                </div>

                                <div className="browse-name-industry">
                                    <div className="browse-name">{business.businessName}</div>
                                    <div className="browse-industry">{business.industry}</div>
                                </div>

                                <div className="browse-financial-info">
                                    <div><strong>Funding Goal: </strong>{formatCurrency(business.fundingGoal)}</div>
                                    <div><strong>Revenue: </strong>{formatCurrency(business.currentRevenue)}</div>
                                    <div><strong>Founded: </strong>{business.foundedDate}</div>
                                </div>

                                <div className="browse-action-buttons">
                                    <button onClick={() => onSave(business.id)}>Save</button>
                                    <button onClick={() => handleToggleMore(business.id)}>
                                        {isExpanded ? "Close" : "More"}
                                    </button>
                                </div>
                            </div>

                            {isExpanded && (
                                <div className="browse-description">
                                    <p>{business.description}</p>
                                </div>
                            )}
                        </div>
                    )
                })
            )}
            </div>
        </div>
    );
}
 
export default BrowseBusinesses;