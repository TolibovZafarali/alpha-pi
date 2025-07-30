import './App.css'
import Header from './components/header/Header'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Footer from './components/footer/Footer'
import Home from './pages/homepage/Home'
import News from './pages/news/News'
import Contact from './pages/contact/Contact'
import About from './pages/about/About'

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
