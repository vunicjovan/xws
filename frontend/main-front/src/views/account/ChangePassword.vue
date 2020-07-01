<template>
  <div v-if="isLogged && getUser !== null">
      <form novalidate class="md-layout md-alignment-top-center" @submit.prevent="validateData">
          <md-card class="md-layout-item md-size-30 md-small-size-100">
              <md-card-header>
                  <div class="md-title">Change Password</div>
              </md-card-header>
              <md-card-content>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('oldPassword')">
                          <label for="oldPassword">Current password</label>
                          <md-input id="oldPassword" name="oldPassword" type="password" v-model="form.oldPassword" />
                          <span class="md-error" v-if="!$v.form.oldPassword.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.oldPassword.sqli">At least 1: capital letter, digit and special character (#!?)</span>
                          <span class="md-error" v-else-if="!$v.form.oldPassword.minLength">Password requires at least 10 characters</span>
                      </md-field>
                  </div>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('newPassword')">
                          <label for="newPassword">New password</label>
                          <md-input id="newPassword" name="newPassword" type="password" v-model="form.newPassword" />
                          <span class="md-error" v-if="!$v.form.newPassword.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.newPassword.sqli">At least 1: capital letter, digit and special character (#!?)</span>
                          <span class="md-error" v-else-if="!$v.form.newPassword.minLength">Password requires at least 10 characters</span>
                      </md-field>
                  </div>
                  <div class="md-layout-item md-small-size-100">
                      <md-field :class="getValidationClass('newPasswordRetype')">
                          <label for="newPasswordRetype">New password</label>
                          <md-input id="newPasswordRetype" name="newPasswordRetype" type="password" v-model="form.newPasswordRetype" />
                          <span class="md-error" v-if="!$v.form.newPasswordRetype.required">Password is required</span>
                          <span class="md-error" v-else-if="!$v.form.newPasswordRetype.sqli">At least 1: capital letter, digit and special character (#!?)</span>
                          <span class="md-error" v-else-if="!$v.form.newPasswordRetype.minLength">Password requires at least 10 characters</span>
                      </md-field>
                  </div>
              </md-card-content>
              <md-card-actions>
                  <md-button type="submit" class="md-primary" >Save changes</md-button>
              </md-card-actions>
          </md-card>
      </form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { validationMixin } from "vuelidate";
import { required, minLength } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\W+)([a-zA-Z0-9!?#\s?]+)$/);

export default {
    name: "ChangePassword",
    mixins: [validationMixin],
    data() {
        return {
            form: {
                oldPassword: undefined,
                newPassword: undefined,
                newPasswordRetype: undefined,
            }
        }
    },
    computed: {
        ...mapGetters(["isLogged", "getUser"]),
    },
    methods: {
        getValidationClass(fieldName) {
			const field = this.$v.form[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
        },
        validateData() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.$store
				.dispatch("changePassword", this.form)
				.then(() => {
                    this.$router.push("/")
				})
				.catch((error) => console.log(error));
			}
		},
    },
    validations: {
        form: {
            oldPassword: {
				required,
				sqli,
				minLength: minLength(10),
            },
            newPassword: {
				required,
				sqli,
				minLength: minLength(10),
			},
			newPasswordRetype: {
				required,
				sqli,
				minLength: minLength(10),
			},
        }
    }

}
</script>

<style>

</style>
