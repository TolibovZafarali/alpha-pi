import { useEffect, useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { getAuth } from "../../utils/auth";
import "./Header.css";
import { logout } from "../../services/authService";

const Header = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [menuOpen, setMenuOpen] = useState(false);
  const [menuVisible, setMenuVisible] = useState(false);
  const [hideSignout, setHideSignout] = useState(false);

  useEffect(() => {
    const loadUser = () => {
      const { id, role, email } = getAuth();
      if (id && role) setUser({ id, role, email });
      else setUser(null);
    };
    loadUser();
    window.addEventListener("storage", loadUser);
    window.addEventListener("authChanged", loadUser);
    return () => {
      window.removeEventListener("storage", loadUser);
      window.removeEventListener("authChanged", loadUser);
    };
  }, []);

  const handleLogout = async () => {
    try { await logout(); } catch {}
    setUser(null);
    setMenuOpen(false);
    navigate("/");
  };

  const openMenu = () => { setMenuVisible(true); setMenuOpen(true); };
  const closeMenu = () => { setMenuOpen(false); setTimeout(() => setMenuVisible(false), 300); };

  const handleProfileClick = (e) => {
    setHideSignout(true);
    if (e?.currentTarget?.blur) e.currentTarget.blur();
    setTimeout(() => setHideSignout(false), 350);
  }

  const dashboardPath =
    user?.role === "BUSINESS" ? "/business/dashboard"
    : user?.role === "INVESTOR" ? "/investor/dashboard"
    : null;

  return (
    <header className="header">
      {/* Left cluster: Profile (desktop) + slide-out Sign Out, or Sign Up / Login */}
      <div className="header-left">
        <div className="auth-buttons">
          {user ? (
            <div className={`profile-group ${hideSignout ? "hide-signout" : ""}`} aria-label="Profile actions">
              {dashboardPath && (
                <NavLink to={dashboardPath} className="auth-link profile-link" onClick={handleProfileClick}>
                  Profile
                </NavLink>
              )}
              <button className="signout-slide" onClick={handleLogout}>
                Sign Out
              </button>
            </div>
          ) : (
            <>
              <NavLink to="/signup" className="auth-link">Sign Up</NavLink>
              <NavLink to="/login" className="auth-link">Login</NavLink>
            </>
          )}
        </div>
      </div>

      {/* Centered logo */}
      <Link to="/" className="logo-link">
        <img src="/alpha-pi-logo.svg" alt="Alpha-Pi Logo" className="logo" />
      </Link>

      {/* Right nav (no Profile here on desktop) */}
      <div className="header-right">
        <nav className="topnav">
          <NavLink to="/" end>Home</NavLink>
          <NavLink to="/news">News</NavLink>
          <NavLink to="/contact">Contact</NavLink>
          <NavLink to="/about">About</NavLink>
        </nav>
      </div>

      {/* Mobile hamburger */}
      <button className="hamburger" onClick={openMenu}>☰</button>

      {/* Mobile slide-out menu (keeps Profile + Sign Out visible as before) */}
      {menuVisible && (
        <div className={`mobile-menu ${menuOpen ? "slide-in" : "slide-out"}`}>
          <div className="mobile-menu-top">
            <button className="close-btn" onClick={closeMenu}>×</button>
            <nav className="mobile-nav">
              <NavLink to="/" end onClick={closeMenu}>Home</NavLink>
              <NavLink to="/news" onClick={closeMenu}>News</NavLink>
              <NavLink to="/contact" onClick={closeMenu}>Contact</NavLink>
              <NavLink to="/about" onClick={closeMenu}>About</NavLink>
              {dashboardPath && (
                <NavLink to={dashboardPath} onClick={closeMenu}>Profile</NavLink>
              )}
            </nav>
          </div>
          <div className="mobile-auth">
            {user ? (
              <button className="auth-link" onClick={() => { handleLogout(); closeMenu(); }}>
                Sign Out
              </button>
            ) : (
              <>
                <NavLink to="/signup" onClick={closeMenu} className="auth-link">Sign Up</NavLink>
                <NavLink to="/login" onClick={closeMenu} className="auth-link">Login</NavLink>
              </>
            )}
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;
