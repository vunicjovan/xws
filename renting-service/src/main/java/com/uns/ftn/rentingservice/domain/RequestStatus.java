package com.uns.ftn.rentingservice.domain;

public enum RequestStatus {

   pending(0),
   reserved(1),
   paid(2),
   canceled(3);

   private int code;

   RequestStatus(int code) {
      this.code = code;
   }
}