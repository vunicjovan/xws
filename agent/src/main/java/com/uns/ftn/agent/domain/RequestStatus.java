package com.uns.ftn.agent.domain;
/***********************************************************************
 * Module:  RequestStatus.java
 * Author:  Vunic
 * Purpose: Defines the Class RequestStatus
 ***********************************************************************/

import java.util.*;

public enum RequestStatus {
   pending,
   reserved,
   paid,
   canceled;
}