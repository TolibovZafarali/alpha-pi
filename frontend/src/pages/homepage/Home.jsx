import { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css";
import { getAuth } from "../../utils/auth";
import { getMyInvestor } from "../../services/investorService";
import { getMyBusiness } from "../../services/businessService";

const Home = () => {
  const [user, setUser] = useState(null);
  const [contactName, setContactName] = useState("");
  const videoRef = useRef(null);

  useEffect(() => {
    const checkAuth = async () => {
      const auth = getAuth();
      if (auth.accessToken && auth.role) {
        setUser(auth);
        try {
          if (auth.role === "BUSINESS") {
            const res = await getMyBusiness();
            setContactName(res.data?.contactName || "");
          } else if (auth.role === "INVESTOR") {
            const res = await getMyInvestor();
            setContactName(res.data?.contactName || "");
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
    };
  }, []);

  const firstName = contactName?.split(" ")[0] || "there";
  const dashboardPath =
    user?.role === "BUSINESS"
      ? "/business/dashboard"
      : user?.role === "INVESTOR"
      ? "/investor/dashboard"
      : null;

  // Freeze on last frame when the video ends
  const handleEnded = () => {
    const v = videoRef.current;
    if (!v) return;
    try {
      // Safari/Chrome can repaint black on 'ended'; bump just before the real end
      v.pause();
      const lastMoment = Math.max(0, v.duration - 0.05);
      if (!Number.isNaN(lastMoment) && Number.isFinite(lastMoment)) {
        v.currentTime = lastMoment;
      }
    } catch (e) {
      console.warn("Could not freeze last frame:", e);
    }
  };

  return (
    <>
      <div className="home-container">
        {/* Background video (served from /public/assets) */}
        <video
          className="bg-video"
          ref={videoRef}
          autoPlay
          muted
          playsInline
          preload="auto"
          poster="/assets/building-4.jpg"
          onEnded={handleEnded}
        >
          <source src="/assets/city-4k.mp4" type="video/mp4" />
        </video>

        {/* Subtle dark overlay so text stays readable */}
        <div className="home-scrim" />

        <div className="overlay-box">
          {user ? (
            <>
              <h1>Welcome back, {firstName}!</h1>
              <p>
                We’re excited to help you connect, invest, and grow your future — one opportunity at a time.
              </p>
              <hr />
              {dashboardPath && (
                <Link to={dashboardPath}>
                  <button className="dashboard-btn">Dashboard</button>
                </Link>
              )}
            </>
          ) : (
            <>
              <h1>Welcome to Alpha-Pi</h1>
              <p>
                Where small businesses and investors meet, share ideas, and grow together. Join our community to discover opportunities that truly matter.
              </p>
              <hr />
              <p className="act-on-reality">Act on Reality</p>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default Home;
