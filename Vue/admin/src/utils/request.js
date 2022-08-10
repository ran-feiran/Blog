import Axios from "axios";

const request = Axios.create({
  baseURL: '/api',
  timeout: 3000,
  withCredentials: true, // default
});


export  default  request;
