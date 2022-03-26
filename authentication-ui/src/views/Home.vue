<template>
  <div class="container">
    <Header />
    <h1>{{ title }}</h1>
    <div id="option">
      <h2>Or </h2>
      <button @click="onClick()" class="btn">{{ option }}</button>
    </div>
    <SignInForm v-show="showSignIn" @signin="signIn" />
    <SignUpForm v-show="showSignUp" @signup="signUp"/>
    <ResetButton />
  </div>
</template>

<script>
import Header from "../components/Header"
import SignInForm from "../components/SignInForm"
import SignUpForm from "../components/SignUpForm"
import axios from "axios"
import swal from "sweetalert"

export default {
  name: 'Home',
  components: {
    Header,
    SignInForm,
    SignUpForm,
  },
  data() {
    return {
      title: 'Login',
      option: 'Register',
      showSignIn: true,
      showSignUp: false,
    }
  },
  methods: {
    async signIn(user) {
      await axios
        .post('api/users/signin', user)
        .then(() => {
          this.$router.push({name: "MyPage", params: {username: this.username}})
          swal({
            text: "User signin successful",
            icon: "success",
          });
        })
        .catch((err) => console.log("err", err));
    },
    async signUp(user) {
      await axios
        .post('api/users/signup', user)
        .then(() => {
          this.$router.push({name: "MyPage", params: {username: this.username}})
          swal({
            text: "User signup successful",
            icon: "success",
          });
        })
        .catch((err) => console.log("err", err));
    },    
    onClick() {
      this.showSignIn = !this.showSignIn
      this.showSignUp = !this.showSignUp
      if (this.showSignUp) {
        this.title = 'Register'
        this.option = 'Login'
      }
      else {
        this.title = 'Login'
        this.option = 'Register'        
      }
    }
  }
}
</script>

<style>
.container {
  background-color: rgb(80, 80, 80);
  color: white;
  width: 70%;
  height: 440px;
  padding: 20px;
  font-family: 'Montserrat', sans-serif;
  margin: auto;
  margin-top: 100px;
}

#option {
  display: flex;
}

h1 {
  font-family: 'Poppins', sans-serif;
}

h2 {
  font-size: 1.1em;
}

.btn {
  color: dodgerblue;
  background: none;
  border: none;
  font-size: 1.1em;
  padding: 0;
}
</style>