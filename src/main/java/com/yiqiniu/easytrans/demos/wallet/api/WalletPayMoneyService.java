package com.yiqiniu.easytrans.demos.wallet.api;

import java.io.Serializable;

/**
 * define an interface for calling wallet
 * the constraint of this interface is that:
 * 1. only contains one method
 * 2. the method only has one parameter, it's not basic class and implements Serializable interface
 * 3. the return parameter should not be basic class too, and it has to implements Serializable interface
 * 4. the return parameter can also be Future<>, the generalization parameter class should be like point 3
 */
public interface WalletPayMoneyService {
	
	public static final String APPID = "wallet";

	WalletPayResponseVO pay(WalletPayRequestVO request);

	public static class WalletPayRequestVO implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer userId;

		private Integer payAmount;

		public Integer getPayAmount() {
			return payAmount;
		}

		public void setPayAmount(Integer payAmount) {
			this.payAmount = payAmount;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}
	}

	public static class WalletPayResponseVO implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer freezeAmount;

		public Integer getFreezeAmount() {
			return freezeAmount;
		}

		public void setFreezeAmount(Integer freezeAmount) {
			this.freezeAmount = freezeAmount;
		}

		@Override
		public String toString() {
			return "WalletPayTccMethodResult [freezeAmount=" + freezeAmount + "]";
		}
	}
}
