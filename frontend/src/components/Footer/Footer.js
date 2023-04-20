import React from 'react';

const Footer = () => {
  return (
    <footer>
      <div className="footer-container">
        <p>&copy; {new Date().getFullYear()} ArgueWise. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
