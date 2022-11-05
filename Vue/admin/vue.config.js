const compression = require("compression-webpack-plugin");
module.exports = {
  configureWebpack: {
    plugins:[
      new compression({
        test: /\.(js|css)(\?.*)?$/i, // 需要压缩的文件正则
        threshold:10240, // 文件大于这个值使用压缩
        deleteOriginalAssets: false // 压缩后保留源文件
      })
    ]
  },
  devServer: {
    port: 8090,
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""
        }
      }
    },
    disableHostCheck: true
  }
};

const path = require("path");
function resolve(dir) {
  return path.join(__dirname, dir);
}
