import axios from "axios";

export default {
    getSimpleSearchResults(params) {
        return axios.get("/search/simple", params).then((response) => {
            return response.data;
        });
    },

    getAdvancedSearchResults(params) {
        return axios.get("/search/advandced", params).then((response) => {
            return response.data;
        });
    }
};