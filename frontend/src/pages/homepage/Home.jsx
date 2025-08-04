import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css"
import { getAuth } from "../../utils/auth";
import { getInvestorProfile } from "../../services/investorService";
import { getBusinessProfile } from "../../services/businessService";

const Home = () => {
    
    const [user, setUser] = useState(null);
    const [contactName, setContactName] = useState("");

    useEffect(() => {
        const checkAuth = async () => {
            const auth = getAuth();
            if (auth.id && auth.type) {
                setUser(auth);

                try {
                    if (auth.type === "business") {
                        const res = await getBusinessProfile(auth.id);
                        setContactName(res.data.contactName);
                    } else if (auth.type === "investor") {
                        const res = await getInvestorProfile(auth.id);
                        setContactName(res.data.contactName);
                    }
                } catch (err) {
                    console.error("Failed to fetch profile: ", err);
                }
            } else {
                setUser(null);
                setContactName("");
            }
        };

        checkAuth();
        window.addEventListener("storage", checkAuth);
        window.addEventListener("authChanged", checkAuth);

        return () => {
            window.removeEventListener("storage", checkAuth);
            window.removeEventListener("authChanged", checkAuth);
        }
    }, []);

    const firstName = contactName.split(" ")[0];
    
    return (
        <>
            <div className="home-container">
                <div className="overlay-box">
                    {user ? (
                        <>
                            <h1>Welcome back, {firstName}!</h1>
                            <p>We’re excited to help you connect, invest, and grow your future — one opportunity at a time.</p>
                            <hr />
                            <Link to={`/${user.type}/${user.id}/dashboard`}>
                                <button className="dashboard-btn">Dashboard</button>
                            </Link>
                        </>
                    ) : (
                        <>
                            <h1>Welcome to Alpha-Pi!</h1>
                            <p>
                                Where small businesses and investors meet, share ideas,
                                and grow together. Join our community to discover opportunities that truly matter.
                            </p>
                            <hr />
                            <p className="act-on-reality">Act on Reality</p>
                        </>
                    )}
                </div>
            </div>
        </>
    );
}
 
export default Home;