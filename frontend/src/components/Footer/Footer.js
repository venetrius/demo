import React from 'react';
import { Layout } from 'antd';

const { Footer } = Layout;

const FooterComponent = () => {
  return (
    <Footer style={{ textAlign: 'center', padding: '12px 50px' }}>
      &copy; {new Date().getFullYear()} ArgueWise. All rights reserved.
    </Footer>
  );
};

export default FooterComponent;