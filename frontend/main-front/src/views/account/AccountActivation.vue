<template>
    <div v-if='this.msg !== ""'>
        <div class="md-headline">{{ this.msg }}</div>
    </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
    name: "AccountActivation",
    data: function() {
		return {
            msg: "",
            counter: 3,
        };
    },
    mounted() {
        if (this.$route.params.token !== "") {
            this.$store.dispatch("activateAccount", this.$route.params.token)
            .then(data => {
                this.msg = "Activation successful.\nPlease sign in to use our services.";
            })
            .catch(error => {
                this.msg = "Requested verification token is invalid or expired.\nPlease send new registration request.";
            });
        }
    },
    watch: {
        counter: {
            handler(value) {
                if (value > 0) {
                    setTimeout(() => {
                        this.counter--;
                    }, 1000);
                }
                else if (value === 0) {
                    this.$router.push("/");
                }
            },
            immediate: true
        }
    }
}
</script>

<style>

</style>
