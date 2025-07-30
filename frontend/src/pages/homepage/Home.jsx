import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css"
import { getAuth } from "../../utils/auth";

const Home = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const auth = getAuth();
        if (auth.id && auth.type) {
            setUser(auth)
        }
    }, []);
    
    return (
        <>
            <div className="home-container">
                <div className="overlay-box">
                    {user ? (
                        <>
                            <h1>Welcome back, {user.name}!</h1>
                            <p>We’re excited to help you connect, invest, and grow your future — one opportunity at a time.</p>
                            <hr />
                            <Link to={`/${user.type}/dashboard`}>
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