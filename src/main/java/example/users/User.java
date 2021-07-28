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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * A {@link User} domain object. The primary entity of this example. Basically a combination of a
 * {@link Username} and {@link Password}.
 *
 * @author Oliver Gierke
 */
@Entity
public class User {
	@GeneratedValue
	@Id
	private Long id;
	private final Username username;
	private final Password password;

	User() {
		this.username = null;
		this.password = null;
	}

	/**
	 * Makes sure only {@link User}s with encrypted {@link Password} can be persisted.
	 */
	@PrePersist
	@PreUpdate
	void assertEncrypted() {
		if (!password.isEncrypted()) {
			throw new IllegalStateException(
					"Tried to persist/load a user with a non-encrypted password!");
		}
	}

	public Long getId() {
		return this.id;
	}

	public Username getUsername() {
		return this.username;
	}

	public Password getPassword() {
		return this.password;
	}

	public User(final Username username, final Password password) {
		this.username = username;
		this.password = password;
	}

	User(final Long id, final Username username, final Password password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	@java.lang.Override
	public boolean equals(final java.lang.Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User))
			return false;
		final User other = (User) o;
		if (!other.canEqual((java.lang.Object) this))
			return false;
		final java.lang.Object this$id = this.getId();
		final java.lang.Object other$id = other.getId();
		if (this$id == null ? other$id != null : !this$id.equals(other$id))
			return false;
		return true;
	}

	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof User;
	}

	@java.lang.Override
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $id = this.getId();
		result = result * PRIME + ($id == null ? 43 : $id.hashCode());
		return result;
	}
}
