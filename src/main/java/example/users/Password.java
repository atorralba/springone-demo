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

/**
 * A value object to represent {@link Password}s in encrypted and unencrypted state. Note how the
 * methods to create a {@link Password} in encrypted state are restricted to package scope so that
 * only the user subsystem is actually able to encrypted passwords.
 *
 * @author Oliver Gierke
 */
@Embeddable
public class Password implements CharSequence {
	private final String password;
	private transient boolean encrypted;

	Password() {
		this.password = null;
		this.encrypted = true;
	}

	/**
	 * Creates a new raw {@link Password} for the given source {@link String}.
	 *
	 * @param password must not be {@literal null} or empty.
	 * @return
	 */
	public static Password raw(String password) {
		return new Password(password, false);
	}

	/**
	 * Creates a new encrypted {@link Password} for the given {@link String}. Note how this method
	 * is package protected so that encrypted passwords can only created by components in this
	 * package and not accidentally by clients using the type from other packages.
	 *
	 * @param password must not be {@literal null} or empty.
	 * @return
	 */
	static Password encrypted(String password) {
		return new Password(password, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return encrypted ? password : "********";
	}

	@java.lang.Override
	public boolean equals(final java.lang.Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Password))
			return false;
		final Password other = (Password) o;
		if (!other.canEqual((java.lang.Object) this))
			return false;
		final java.lang.Object this$password = this.getPassword();
		final java.lang.Object other$password = other.getPassword();
		if (this$password == null ? other$password != null : !this$password.equals(other$password))
			return false;
		return true;
	}

	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof Password;
	}

	@java.lang.Override
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $password = this.getPassword();
		result = result * PRIME + ($password == null ? 43 : $password.hashCode());
		return result;
	}

	private Password(final String password, final boolean encrypted) {
		this.password = password;
		this.encrypted = encrypted;
	}

	String getPassword() {
		return this.password;
	}

	public boolean isEncrypted() {
		return this.encrypted;
	}

	public int length() {
		return this.getPassword().length();
	}

	public boolean isEmpty() {
		return this.getPassword().isEmpty();
	}

	public char charAt(final int index) {
		return this.getPassword().charAt(index);
	}

	public int codePointAt(final int index) {
		return this.getPassword().codePointAt(index);
	}

	public int codePointBefore(final int index) {
		return this.getPassword().codePointBefore(index);
	}

	public int codePointCount(final int beginIndex, final int endIndex) {
		return this.getPassword().codePointCount(beginIndex, endIndex);
	}

	public int offsetByCodePoints(final int index, final int codePointOffset) {
		return this.getPassword().offsetByCodePoints(index, codePointOffset);
	}

	public void getChars(final int srcBegin, final int srcEnd, final char[] dst,
			final int dstBegin) {
		this.getPassword().getChars(srcBegin, srcEnd, dst, dstBegin);
	}

	@java.lang.Deprecated
	public void getBytes(final int srcBegin, final int srcEnd, final byte[] dst,
			final int dstBegin) {
		this.getPassword().getBytes(srcBegin, srcEnd, dst, dstBegin);
	}

	public byte[] getBytes(final java.lang.String charsetName)
			throws java.io.UnsupportedEncodingException {
		return this.getPassword().getBytes(charsetName);
	}

	public byte[] getBytes(final java.nio.charset.Charset charset) {
		return this.getPassword().getBytes(charset);
	}

	public byte[] getBytes() {
		return this.getPassword().getBytes();
	}

	public boolean contentEquals(final java.lang.StringBuffer sb) {
		return this.getPassword().contentEquals(sb);
	}

	public boolean contentEquals(final java.lang.CharSequence cs) {
		return this.getPassword().contentEquals(cs);
	}

	public boolean equalsIgnoreCase(final java.lang.String anotherString) {
		return this.getPassword().equalsIgnoreCase(anotherString);
	}

	public int compareTo(final java.lang.String anotherString) {
		return this.getPassword().compareTo(anotherString);
	}

	public int compareToIgnoreCase(final java.lang.String str) {
		return this.getPassword().compareToIgnoreCase(str);
	}

	public boolean regionMatches(final int toffset, final java.lang.String other, final int ooffset,
			final int len) {
		return this.getPassword().regionMatches(toffset, other, ooffset, len);
	}

	public boolean regionMatches(final boolean ignoreCase, final int toffset,
			final java.lang.String other, final int ooffset, final int len) {
		return this.getPassword().regionMatches(ignoreCase, toffset, other, ooffset, len);
	}

	public boolean startsWith(final java.lang.String prefix, final int toffset) {
		return this.getPassword().startsWith(prefix, toffset);
	}

	public boolean startsWith(final java.lang.String prefix) {
		return this.getPassword().startsWith(prefix);
	}

	public boolean endsWith(final java.lang.String suffix) {
		return this.getPassword().endsWith(suffix);
	}

	public int indexOf(final int ch) {
		return this.getPassword().indexOf(ch);
	}

	public int indexOf(final int ch, final int fromIndex) {
		return this.getPassword().indexOf(ch, fromIndex);
	}

	public int lastIndexOf(final int ch) {
		return this.getPassword().lastIndexOf(ch);
	}

	public int lastIndexOf(final int ch, final int fromIndex) {
		return this.getPassword().lastIndexOf(ch, fromIndex);
	}

	public int indexOf(final java.lang.String str) {
		return this.getPassword().indexOf(str);
	}

	public int indexOf(final java.lang.String str, final int fromIndex) {
		return this.getPassword().indexOf(str, fromIndex);
	}

	public int lastIndexOf(final java.lang.String str) {
		return this.getPassword().lastIndexOf(str);
	}

	public int lastIndexOf(final java.lang.String str, final int fromIndex) {
		return this.getPassword().lastIndexOf(str, fromIndex);
	}

	public java.lang.String substring(final int beginIndex) {
		return this.getPassword().substring(beginIndex);
	}

	public java.lang.String substring(final int beginIndex, final int endIndex) {
		return this.getPassword().substring(beginIndex, endIndex);
	}

	public java.lang.CharSequence subSequence(final int beginIndex, final int endIndex) {
		return this.getPassword().subSequence(beginIndex, endIndex);
	}

	public java.lang.String concat(final java.lang.String str) {
		return this.getPassword().concat(str);
	}

	public java.lang.String replace(final char oldChar, final char newChar) {
		return this.getPassword().replace(oldChar, newChar);
	}

	public boolean matches(final java.lang.String regex) {
		return this.getPassword().matches(regex);
	}

	public boolean contains(final java.lang.CharSequence s) {
		return this.getPassword().contains(s);
	}

	public java.lang.String replaceFirst(final java.lang.String regex,
			final java.lang.String replacement) {
		return this.getPassword().replaceFirst(regex, replacement);
	}

	public java.lang.String replaceAll(final java.lang.String regex,
			final java.lang.String replacement) {
		return this.getPassword().replaceAll(regex, replacement);
	}

	public java.lang.String replace(final java.lang.CharSequence target,
			final java.lang.CharSequence replacement) {
		return this.getPassword().replace(target, replacement);
	}

	public java.lang.String[] split(final java.lang.String regex, final int limit) {
		return this.getPassword().split(regex, limit);
	}

	public java.lang.String[] split(final java.lang.String regex) {
		return this.getPassword().split(regex);
	}

	public java.lang.String toLowerCase(final java.util.Locale locale) {
		return this.getPassword().toLowerCase(locale);
	}

	public java.lang.String toLowerCase() {
		return this.getPassword().toLowerCase();
	}

	public java.lang.String toUpperCase(final java.util.Locale locale) {
		return this.getPassword().toUpperCase(locale);
	}

	public java.lang.String toUpperCase() {
		return this.getPassword().toUpperCase();
	}

	public java.lang.String trim() {
		return this.getPassword().trim();
	}

	public java.util.stream.IntStream chars() {
		return this.getPassword().chars();
	}

	public java.util.stream.IntStream codePoints() {
		return this.getPassword().codePoints();
	}

	public char[] toCharArray() {
		return this.getPassword().toCharArray();
	}

	public java.lang.String intern() {
		return this.getPassword().intern();
	}
}
