import axios from "axios";

export default {
    getCart(cartId) {
        return axios.get(`/rent/${cartId}`).then((response) => {
            return response.data;
        })
    },

    getCartAdvertisements(advertisements) {
        return axios.get("/view/cart", { params: advertisements }).then((response) => {
            return response.data;
        })
    },

    addCartItem(userId, advetrisementId) {
        return axios.post(`/rent/${userId}/item/${advetrisementId}`).then((response) => {
            return response.data;
        })
    },

    deleteCartItem(cartId, advetrisementId) {
        return axios.delete(`/rent/${cartId}/item/${advetrisementId}`).then((response) => {
            return response.data;
        })
    },

};