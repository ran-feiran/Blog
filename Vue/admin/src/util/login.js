import axios from "axios";
import store from "../store/index"

export const loadLoginPage = () =>{
    axios.get("/api/blogInfo/loadLoginPage").then(({ data }) => {
        store.commit("loadLoginPage",data.data)
    })
}

