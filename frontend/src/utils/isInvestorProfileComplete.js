import InvestorProfileBlueprint from "../models/InvestorProfileBlueprint";

const isInvestorProfileComplete = (profile) => {
    if (!profile) return false;

    const blueprint = new InvestorProfileBlueprint(
        "", "", "", "", "", "", ""
    );

    for (let key of Object.keys(blueprint)) {
        if (profile[key] === undefined || profile[key] === null) return false;
    }

    return true;
};


 
export default isInvestorProfileComplete;