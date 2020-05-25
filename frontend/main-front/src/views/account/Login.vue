<template>
    <div v-if="!logged">
        <transition name="fade">
            <form v-if="show" novalidate class="md-layout md-alignment-top-center" @submit.prevent="validateUser">
                <md-card class="md-layout-item md-size-30 md-small-size-100">
                    <md-card-header>
                        <div class="md-title">Login</div>
                    </md-card-header>
                    <md-card-content>
                        <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('email')">
                                <label for="first-name">E-mail</label>
                                <md-input type="email" name="email" id="email" v-model="form.email" autocomplete="email" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.email.required">Email is required</span>
                                <span class="md-error" v-else-if="!$v.form.email.email">Invalid email format</span>
                            </md-field>
                        </div>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('password')">
                                <label for="first-name">Password</label>
                                <md-input type="password" name="password" id="password" v-model="form.password" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.password.required">Password is required</span>
                                <span class="md-error" v-else-if="!$v.form.password.alpha">Invalid password format</span>
                                <span class="md-error" v-else-if="!$v.form.password.minLength">Password requires at least 8 characters</span>
                            </md-field>
                        </div>
                    </md-card-content>
                    <md-card-actions>
                        <md-button type="submit" class="md-primary">Login</md-button>
                    </md-card-actions>
                </md-card>
                <md-snackbar :md-active.sync="userLogged">Welcome, {{ lastUser }}!</md-snackbar>
            </form>
        </transition>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import {
    required,
    minLength,
    email
} from 'vuelidate/lib/validators'
import { helpers } from 'vuelidate/lib/validators'
const sqli = helpers.regex('alpha', /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE)([a-zA-Z0-9\\!\\?\\#\s?]+)$/)

import { EventBus } from '../../event-bus.js'

export default {
    name: "Login",
    mixins: [validationMixin],
    data: function() {
        return {
            form: {
                email: undefined,
                password: undefined
            },
            userLogged: false,
            sending: false,
            lastUser: null,
            show: false,
            logged: undefined,
        }
    },
    mounted: function() {
        this.fadeMe();

        if (localStorage.getItem("auth")) this.logged = true;
        else this.logged = false;
    },
    methods: {
        fadeMe: function() {
            this.show = !this.show
        },
        login: function() {
            this.sending = true;

            window.setTimeout(() => {
                this.lastUser = `${this.form.email}`
                this.userLogged = true
                this.sending = false
            }, 1500)

            this.axios.post("http://localhost:8089/account/login", this.form)
                      .then(response => {
                          if (response.data != null) {
                              localStorage.setItem('auth', 'Bearer ' + response.data.jwt);
                          }
                          EventBus.$emit("logged", true);
                          this.$router.push('/');
                      })
                      .catch(() => {
                          alert('Login has failed!');
                      })
            
        },
        getValidationClass (fieldName) {
            const field = this.$v.form[fieldName]

            if (field) {
                return {
                    'md-invalid': field.$invalid && field.$dirty
                }
            }
        },
        validateUser () {
            this.$v.$touch()

            if (!this.$v.$invalid) {
                this.login();
            }
        }
    },
    validations: {
      form: {
        email: {
          required,
          email
        },
        password: {
            required,
            sqli,
            minLength: minLength(8)
        }
      }
    }
}
</script>

<style scoped>
    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 2s
    }

    .fade-enter,
    .fade-leave-to {
        opacity: 0
    }
</style>
