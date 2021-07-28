/*
 * Copyright 2015-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package example.users;

import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;

/**
 * value object to represent user names.
 *
 * @author Oliver Gierke
 */
@Embeddable
public class Username {
	private final String username;

	Username() {
		this.username = null;
	}

	/**
	 * Creates a new {@link Username}.
	 *
	 * @param username must not be {@literal null} or empty.
	 */
	public Username(String username) {
		if (!StringUtils.hasText(username)) {
			throw new IllegalArgumentException("Invalid username!");
		}
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return username;
	}

	@java.lang.Override
	public boolean equals(final java.lang.Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Username))
			return false;
		final Username other = (Username) o;
		if (!other.canEqual((java.lang.Object) this))
			return false;
		final java.lang.Object this$username = this.username;
		final java.lang.Object other$username = other.username;
		if (this$username == null ? other$username != null : !this$username.equals(other$username))
			return false;
		return true;
	}

	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Username;
	}

	@java.lang.Override
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $username = this.username;
		result = result * PRIME + ($username == null ? 43 : $username.hashCode());
		return result;
	}
}
