<template>
   <div v-if="getUser">
		<form novalidate class="md-layout md-alignment-top-center">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="md-title">Profile</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout-item md-small-size-100">
						<md-field>
							<label for="first-name">First name</label>
							<md-input type="text" name="first-name" id="first-name" v-model="form.firstName" :disabled="sending" />
						</md-field>
					</div>
					<div class="md-layout-item md-small-size-100">
						<md-field>
							<label for="last-name">Last name</label>
							<md-input type="text" name="last-name" id="last-name" v-model="form.lastName" :disabled="sending" />
						</md-field>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button class="md-primary" @click="updateUser">Update</md-button>
				</md-card-actions>
			</md-card>
		</form>
	</div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
    data() {
        return {
            sending: false,
            form: {
                firstName: undefined,
                lastName: undefined,
            }
        }
    },
    mounted() {
        if (this.getUser) {
            this.form.firstName = this.getUser.firstName;
            this.form.lastName = this.getUser.lastName;
        }
    },
    computed: {
        ...mapGetters(["getUser"]),

    },
    watch: {
        getUser(user) {
            this.form.firstName = this.getUser.firstName;
            this.form.lastName = this.getUser.lastName;
        }
    },
    methods: {
        ...mapActions(["updateUser"]),
        updateUser() {
            this.sending = true;
            var updateDTO = {
                "id": this.getUser.id,
                "firstName": this.form.firstName,
                "lastName": this.form.lastName
            };

            console.log(updateDTO);
            //this.$store.dispatch("updateUser", updateDTO);
            this.sending = false;
        }
    }
}
</script>

<style>

</style>