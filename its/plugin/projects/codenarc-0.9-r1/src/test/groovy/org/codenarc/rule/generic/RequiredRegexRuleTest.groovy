/*
 * Copyright 2009 the original author or authors.
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
package org.codenarc.rule.generic

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for RequiredRegexRule
 *
 * @author Chris Mair
 * @version $Revision: 292 $ - $Date: 2010-01-17 06:09:23 +0300 (Вс, 17 янв 2010) $
 */
class RequiredRegexRuleTest extends AbstractRuleTestCase {
    static final REGEX = /\@author Joe/
    def skipTestThatUnrelatedCodeHasNoViolations

    void testRuleProperties() {
        assert rule.priority == 3
        assert rule.name == 'RequiredRegex'
    }

    void testRegexIsNull() {
        final SOURCE = 'class MyClass { } '
        rule.regex = null
        assert !rule.ready
        assertNoViolations(SOURCE)
    }

    void testRegexIsPresent() {
        final SOURCE = '''
            /** @author Joe */
            class MyClass {
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testRegexIsNotPresent() {
        final SOURCE = '''
            /** @author Mike */
            class MyClass {
            }
        '''
        assertSingleViolation(SOURCE) { v -> containsAll(v.message, ['regular expression', REGEX]) }
    }

    protected Rule createRule() {
        return new RequiredRegexRule(regex:REGEX)
    }

}