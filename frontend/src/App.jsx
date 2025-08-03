import './App.css'
import Header from './components/header/Header'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Footer from './components/footer/Footer'
import Home from './pages/homepage/Home'
import News from './pages/news/News'
import Contact from './pages/contact/Contact'
import About from './pages/about/About'
import Signup from './pages/authpages/Signup'
import Login from './pages/authpages/Login'
import BusinessDashboard from './pages/business/BusinessDashboard'
import InvestorDashboard from './pages/investor/InvestorDashboard'

function App() {
  return (
    <div className="app-container">
      <Router>
        <div className='layout'>
          <Header />
            <main className='page-content'>
              <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/news' element={<News />} />
                <Route path='/contact' element={<Contact />} />
                <Route path='/about' element={<About />} />
                
                <Route path='/signup' element={<Signup />} />
                <Route path='/login' element={<Login />} />

                <Route path='business/:id/dashboard' element={<BusinessDashboard />} />
                <Route path='investor/:id/dashboard' element={<InvestorDashboard />} />

                <Route path='*' element={<Home />} />
              </Routes>
            </main>
          <Footer />
        </div>
      </Router>
    </div>
  )
}

export default App
