import { useEffect, useState } from "react";
import api from "./api/api";
import "./App.css";

function App() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchActiveCategories();
  }, []);

  const fetchActiveCategories = async () => {
    try {
      const response = await api.get("/categories/active");
      setCategories(response.data);
      setError("");
    } catch (err) {
      setError("Failed to load categories. Please check if backend is running.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <nav className="navbar">
        <h2>Local Service Finder</h2>
        <div className="nav-links">
          <span>Home</span>
          <span>Services</span>
          <span>Admin</span>
        </div>
      </nav>

      <section className="hero-section">
        <div className="hero-content">
          <h1>Find Trusted Local Service Providers</h1>
          <p>
            Book verified providers for plumbing, electrical, cleaning, and
            other home services in your city.
          </p>
          <button>Explore Services</button>
        </div>
      </section>

      <section className="categories-section">
        <h2>Available Service Categories</h2>
        <p className="section-subtitle">
          These categories are loaded from your Spring Boot backend.
        </p>

        {loading && <p className="info-message">Loading categories...</p>}

        {error && <p className="error-message">{error}</p>}

        {!loading && !error && categories.length === 0 && (
          <p className="info-message">No active categories available.</p>
        )}

        <div className="category-grid">
          {categories.map((category) => (
            <div className="category-card" key={category.id}>
              <h3>{category.name}</h3>
              <p>{category.description}</p>
              <span className="status-badge">Active</span>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}

export default App;