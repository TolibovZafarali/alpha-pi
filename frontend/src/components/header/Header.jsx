import { useEffect, useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { clearAuth, getAuth } from "../../utils/auth";
import "./Header.css"
import { logout } from "../../services/authService";

const Header = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [menuOpen, setMenuOpen] = useState(false);
    const [menuVisible, setMenuVisible] = useState(false);

    useEffect(() => {
        const loadUser = () => {
            const { id, role, email } = getAuth();
            if (id && role) {
                setUser({ id, role, email });
            } else {
                setUser(null);
            };
        }

        loadUser();

        window.addEventListener("storage", loadUser);
        window.addEventListener("authChanged", loadUser);

        return () => {
            window.removeEventListener("storage", loadUser);
            window.removeEventListener("authChanged", loadUser);
        };
    }, []);

    const handleLogout = async () => {
        try {
            await logout();
        } catch {
            // ignore
        }
        setUser(null);
        setMenuOpen(false);
        navigate("/");
    };

    const openMenu = () => {
        setMenuVisible(true);
        setMenuOpen(true);
    }

    const closeMenu = () => {
        setMenuOpen(false);
        setTimeout(() => setMenuVisible(false), 300);
    }

    const dashboardPath = user?.role === "BUSINESS" ? "/business/dashboard"
                        : user?.role === "INVESTOR" ? "/investor/dashboard"
                        : null;

    return (
        <header className="header">
            
            {/* Sign Up and Login || Sign Out buttons */}
            <div className="header-left">
                <div className="auth-buttons">
                    {user ? (
                        <button onClick={handleLogout} className="auth-link">Sign Out</button>
                    ) : (
                        <>
                            <NavLink to="/signup" className="auth-link">Sign Up</NavLink>
                            <NavLink to="/login" className="auth-link">Login</NavLink>
                        </>
                    )}
                </div>
            </div>

            {/* Logo links to home */}
            <Link to="/" className="logo-link">
                <img src="/alpha-pi-logo.svg" alt="Alpha-Pi Logo" className="logo" />
            </Link>

            {/* Navigation Links */}
            <div className="header-right">
                <nav className="topnav">
                    <NavLink to="/" end>Home</NavLink>
                    <NavLink to="/news">News</NavLink>
                    <NavLink to="/contact">Contact</NavLink>
                    <NavLink to="/about">About</NavLink>
                    {dashboardPath && (
                                <NavLink to={dashboardPath}>Dashboard</NavLink>
                    )}
                </nav>
            </div>

            {/* Hamburger Menu Icon */}
            <button className="hamburger" onClick={openMenu}>
                ☰
            </button>

            {/* Mobile Slide-out Menu */}
            {menuVisible && (
                <div className={`mobile-menu ${menuOpen ? 'slide-in' : 'slide-out'}`}>
                    <div className="mobile-menu-top">
                        <button className="close-btn" onClick={closeMenu}>×</button>
                        <nav className="mobile-nav">
                            <NavLink to="/" end onClick={closeMenu}>Home</NavLink>
                            <NavLink to="/news" onClick={closeMenu}>News</NavLink>
                            <NavLink to="/contact" onClick={closeMenu}>Contact</NavLink>
                            <NavLink to="/about" onClick={closeMenu}>About</NavLink>
                            {dashboardPath && (
                                <NavLink to={dashboardPath} onClick={closeMenu}>Dashboard</NavLink>
                            )}
                        </nav>
                    </div>
                    <div className="mobile-auth">
                        {user ? (
                            <button className="auth-link" onClick={() => { handleLogout(); closeMenu(); }}>Sign Out</button>
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
}
 
export default Header;