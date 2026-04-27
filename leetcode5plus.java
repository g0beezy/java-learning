// LeetCode 5. 最长回文子串
// 中心扩展法：时间 O(n^2)，额外空间 O(1)（只额外用了一个 char[]，是为了减少 charAt 调用开销）
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;            // 长度 0 或 1，本身就是答案

        char[] c = s.toCharArray();     // 转 char[] 比反复 charAt 更快
        int start = 0, maxLen = 1;      // 至少有一个字符的回文

        for (int i = 0; i < n - 1; i++) {
            // 奇数长度：以 i 为中心
            int l = i - 1, r = i + 1;
            while (l >= 0 && r < n && c[l] == c[r]) { l--; r++; }
            if (r - l - 1 > maxLen) {
                maxLen = r - l - 1;
                start  = l + 1;
            }

            // 偶数长度：以 i, i+1 之间为中心
            l = i; r = i + 1;
            while (l >= 0 && r < n && c[l] == c[r]) { l--; r++; }
            if (r - l - 1 > maxLen) {
                maxLen = r - l - 1;
                start  = l + 1;
            }
        }

        return s.substring(start, start + maxLen);
    }
}
