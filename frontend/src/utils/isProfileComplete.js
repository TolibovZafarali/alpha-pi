import BusinessProfileBlueprint from "../models/BusinessProfileBlueprint";

const isProfileComplete = (profile) => {
    if (!profile) return false;
    for (let key of Object.keys(new BusinessProfileBlueprint())) {
        if (!profile[key]) return false;
    }
    return true;
}
 
export default isProfileComplete;