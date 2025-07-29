import { useEffect, useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { clearAuth, getAuth } from "../../utils/auth";
import "./Header.css"

const Header = () => {
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [menuOpen, setMenuOpen] = useState(false);

    useEffect(() => {
        const { id, type } = getAuth();
        if (id && type) {
            setUser({ id, type });
        } else {
            setUser(null);
        }
    }, []);

    const handleLogout = () => {
        clearAuth();
        setUser(null);
        setMenuOpen(false);
        navigate("/");
    };

    return (
        <header className="header">
            
            {/* Sign Up and Login || Sign Out buttons */}
            <div className="auth-buttons">
                {user ? (
                    <button onClick={handleLogout}>Sign Out</button>
                ) : (
                    <>
                        <NavLink to="/signup" className="auth-link">Sign Up</NavLink>
                        <NavLink to="/login" className="auth-link">Login</NavLink>
                    </>
                )}
            </div>

            {/* Logo links to home */}
            <Link to="/">
                <img src="/alpha-pi-logo.svg" alt="Alpha-Pi Logo" className="logo" />
            </Link>

            {/* Navigation Links */}
            <nav className="topnav">
                <NavLink to="/" end>Home</NavLink>
                <NavLink to="/news">News</NavLink>
                <NavLink to="/contact">Contact</NavLink>
                <NavLink to="/about">About</NavLink>
            </nav>

            {/* Hamburger Menu Icon */}
            <button className="hamburger" onClick={() => setMenuOpen(true)}>
                ☰
            </button>

            {/* Mobile Slide-out Menu */}
            {menuOpen && (
                <div className="mobile-menu">
                    <button className="close-btn" onClick={() => setMenuOpen(false)}>×</button>
                    <nav className="mobile-nav">
                        <NavLink to="/" end onClick={() => setMenuOpen(false)}>Home</NavLink>
                        <NavLink to="/news" onClick={() => setMenuOpen(false)}>News</NavLink>
                        <NavLink to="/contact" onClick={() => setMenuOpen(false)}>Contact</NavLink>
                        <NavLink to="/about" onClick={() => setMenuOpen(false)}>About</NavLink>
                    </nav>

                    <div className="separator"></div>

                    <div className="mobile-auth">
                        {user ? (
                            <button onClick={handleLogout}>Sign Out</button>
                        ) : (
                            <>
                                <NavLink to="/signup" onClick={() => setMenuOpen(false)} className="auth-link">Sign Up</NavLink>
                                <NavLink to="/login" onClick={() => setMenuOpen(false)} className="auth-link">Login</NavLink>
                            </>
                        )}
                    </div>
                </div>
            )}
        </header>
    );
}
 
export default Header;