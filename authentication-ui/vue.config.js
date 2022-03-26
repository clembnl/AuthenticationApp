const fs = require('fs')

module.exports = {
    devServer: {
      proxy: {
        '^/api': {
          target: 'http://localhost:8081',
          changeOrigin: true,
          logLevel: 'debug',
          pathRewrite: { '^/api': '/' },
        },        
      },
      https: {
        key: fs.readFileSync('./certs/localhost+1-key.pem'),
        cert: fs.readFileSync('./certs/localhost+1.pem'),
      },
      public: 'https://localhost:8080/'      
    },
  }