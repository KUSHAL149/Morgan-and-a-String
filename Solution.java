import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	private static final int MAXSIZE = 200100;
	private static final int ALPHABET = 128;
	
    public static void main(String[] args) {
      
    	Scanner in = new Scanner(System.in);
    	int testcase = Integer.parseInt(in.nextLine());
    	for (int i = 0; i < testcase; i++) {
    		String str1 = in.nextLine();
    		String str2 = in.nextLine();
    		System.out.println(solution(str1 + "a", str2 + "b"));
    	}
    }
    private static List<Integer> buildSuffixArray(String str) {
    	int[] p = new int[MAXSIZE];
    	int[] c = new int[MAXSIZE];
    	int[] cnt = new int[MAXSIZE];
    	int[] pn = new int[MAXSIZE];
    	int[] cn = new int[MAXSIZE];
    	Arrays.fill(cnt, 0);
    	int n = str.length();
    	for (int i = 0; i < n; i++) {
    		++cnt[str.charAt(i)];
    	}
    	for (int i = 1; i < ALPHABET; i++) {
    		cnt[i] += cnt[i - 1];
    	}
    	for (int i = 0; i < n; i++) {
    		p[--cnt[str.charAt(i)]] = i;
    	}
    	int count = 1;
    	c[p[0]] = count - 1;
    	for (int i = 1; i < n; i++) {
    		if (str.charAt(p[i]) != str.charAt(p[i - 1])) {
    			++count;
    		}
    		c[p[i]] = count - 1;
    	}
    	for (int h = 0; (1 << h) < n; ++h) {
    		for (int i =0; i < n; i++) {
    			pn[i] = p[i] - (1 << h);
    			if (pn[i] < 0) {
    				pn[i] += n;
    			}
    		}
    		Arrays.fill(cnt, 0);
    		for (int i = 0; i < n; i++) {
    			++cnt[c[i]];
    		}
    		for (int i = 1; i < count; i++) {
    			cnt[i] += cnt[i - 1];
    		}
    		for (int i = n - 1; i >= 0; i--) {
    			p[--cnt[c[pn[i]]]] = pn[i];
    		}
    		count = 1;
    		cn[p[0]] = count - 1;
    		for (int i = 1; i < n; i++) {
    			int pos1 = (p[i] + (1 << h)) % n;
    			int pos2 = (p[i - 1] +  (1 << h)) % n;
    			if (c[p[i]] != c[p[i - 1]] || c[pos1] != c[pos2]) {
    				++count;
    			}
    			cn[p[i]] = count - 1;
    		}
    		for (int i = 0; i < n; i++) {
    			c[i] = cn[i];
    		}
    	}
    	List<Integer> res = new ArrayList<Integer>(n);
    	for (int i = 0; i < n; i++) {
    		res.add(c[i]);
    	}
    	return res;
    }
    
    private static String solution(String str1, String str2) {
    	StringBuilder sb = new StringBuilder(str1).append(str2);
    	List<Integer> suffix = buildSuffixArray(sb.toString());
    	StringBuilder rst = new StringBuilder();
    	int start1 = 0;
    	int start2 = 0;
    	while (start1 < str1.length() - 1 || start2 < str2.length() - 1) {
    		if (start1 >= str1.length() - 1) {
    			rst.append(str2.charAt(start2++));
    			continue;
    		}
    		if (start2 >= str2.length() - 1) {
    			rst.append(str1.charAt(start1++));
    			continue;
    		}
    		if (suffix.get(start1) < suffix.get(str1.length() + start2)) {
    			rst.append(str1.charAt(start1++));
    		} else {
    			rst.append(str2.charAt(start2++));
    		}
    	}
    	return rst.toString();
    }
}
