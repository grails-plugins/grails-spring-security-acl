/* Copyright 2009-2014 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.springsecurity.acl

import grails.test.spock.IntegrationSpec

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder as SCH

import test.TestReport as Report

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
abstract class AbstractAclSpec extends IntegrationSpec {

	protected static final String USER = 'username'
	protected static final String ADMIN = 'admin'

	def aclUtilService

	protected long report1Id
	protected long report2Id

	def setup() {
		report1Id = new Report(name: 'r1').save().id
		report2Id = new Report(name: 'r2').save().id
		assert 2 == Report.count()
	}

	def cleanup() {
		SCH.clearContext()
	}

	protected void loginAsAdmin() {
		login createAdminAuth()
	}

	protected void loginAsUser() {
		login createUserAuth()
	}

	protected void login(Authentication authentication) {
		SCH.context.authentication = authentication
	}

	protected Authentication createAuth(String username, String role) {
		new UsernamePasswordAuthenticationToken(
				username, 'password', [new SimpleGrantedAuthority(role)])
	}

	protected createAdminAuth() {
		createAuth ADMIN, 'ROLE_ADMIN'
	}

	protected createUserAuth() {
		createAuth USER, 'ROLE_USER'
	}
}
