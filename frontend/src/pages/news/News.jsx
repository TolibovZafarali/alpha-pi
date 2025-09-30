import { useEffect, useState } from "react";
import "./News.css";
import fallbackArticles from "../../data/newsFallback.json";

const FALLBACK_MESSAGE =
  "We couldn't load the latest headlines right now. Showing curated highlights instead.";

const MISSING_KEY_MESSAGE =
  "Live headlines are unavailable because the GNews API key is missing. Showing curated highlights instead.";

const formatDate = (value) => {
  if (!value) return "Unknown Date";
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return "Unknown Date";
  return date.toLocaleDateString("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric",
  });
};

const News = () => {
    const [articles, setArticles] = useState([]);
    const [status, setStatus] = useState("");
    const [loading, setLoading] = useState(true);
  
    useEffect(() => {
      let ignore = false;
      const controller = new AbortController();
  
      const showFallback = (message) => {
        if (ignore) return;
        console.warn("Falling back to bundled news feed:", message);
        setArticles(fallbackArticles);
        setStatus(message);
        setLoading(false);
      };
  
      const fetchNews = async () => {
        const apiKey = import.meta.env.VITE_GNEWS_API_KEY;
        if (!apiKey) {
          showFallback(MISSING_KEY_MESSAGE);
          return;
        }
  
        try {
          const API_URL =
            `https://gnews.io/api/v4/search?q=small+business&lang=en&max=8&apikey=${apiKey}`;
          const res = await fetch(API_URL, { signal: controller.signal });
  
          if (!res.ok) {
            throw new Error(`Failed to fetch (${res.status})`);
          }
  
          const json = await res.json();
          if (ignore) return;
  
          const liveArticles = Array.isArray(json.articles) ? json.articles : [];
          if (liveArticles.length === 0) {
            showFallback(FALLBACK_MESSAGE);
            return;
          }
  
          setArticles(liveArticles);
          setStatus("");
          setLoading(false);
        } catch (error) {
          if (ignore) return;
          console.error("Error fetching news:", error);
          showFallback(FALLBACK_MESSAGE);
        }
      };
  
      fetchNews();
  
      return () => {
        ignore = true;
        controller.abort();
      };
    }, []);
  
    return (
      <div className="news-main">
        <h1>News</h1>
        {loading ? (
          <p className="news-status" role="status">
            Loading the latest headlines…
          </p>
        ) : (
          <>
            {status && (
              <p className="news-status" role="status">
                {status}
              </p>
            )}
            <div className="news-layout">
              {articles.length > 0 ? (
                articles.map((news, index) => {
                  const href = news.url || news.link;
                  return (
                    <a
                      key={href || index}
                      href={href || "#"}
                      target={href ? "_blank" : undefined}
                      rel={href ? "noopener noreferrer" : undefined}
                      className="news-btn"
                    >
                      <div className="news-btn-content">
                        <h5>{news.source?.name || "Unknown Source"}</h5>
                        <p>{news.title || "No Title Available"}</p>
                        <small className="news-date">{formatDate(news.publishedAt)}</small>
                      </div>
                    </a>
                  );
                })
              ) : (
                <p className="news-status">No news articles available.</p>
              )}
            </div>
          </>
        )}
      </div>
    );
};

export default News;