const HtmlWebPackPlugin = require("html-webpack-plugin");
module.exports = {
  entry : "./src/index.tsx",
  devtool: "source-map",
  module: {
    rules: [
      {
          test: /\.tsx?$/,  
          use: 'babel-loader',
          exclude: /node_modules/,
      },
      {
          test: /\.jsx?$/,  
          use: 'babel-loader',
          exclude: /node_modules/,
      },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: { loader: "babel-loader" }
      },
      {
        test: /\.html$/,
        use: [{ loader: "html-loader" }]
      },
      {
        test: /\.css$/, 
        use: [ "style-loader", "css-loader" ]
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [ 'file-loader' ]
      }
    ]
  },
  resolve: {
      extensions: ['.ts', '.tsx', '.js', '.jsx'],
  },
  plugins: [
    new HtmlWebPackPlugin ({
      template: "./src/index.html",
      filename: "./index.html"
    })
  ]
};