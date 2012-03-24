package com.mobileapps.pictureonmap;

public class DmsToDegree {

	public String convertToDegree(String dms, String dms_ref) {
		Float result = null;
		String[] DMS = dms.split(",", 3);

		String[] d = DMS[0].split("/", 2);
		Double d0 = new Double(d[0]);
		Double d1 = new Double(d[1]);
		Double floatD = d0 / d1;

		String[] m = DMS[1].split("/", 2);
		Double m0 = new Double(m[0]);
		Double m1 = new Double(m[1]);
		Double floatM = m0 / m1;

		String[] s = DMS[1].split("/", 2);
		Double s0 = new Double(s[0]);
		Double s1 = new Double(s[1]);
		Double floatS = s0 / s1;

		if (dms_ref.equals("S") || dms_ref.equals("W"))
			result = 0 - new Float(floatD + (floatM / 60) + (floatS / 3600));
		else
			result = new Float(floatD + (floatM / 60) + (floatS / 3600));

		return String.valueOf(result);
	}

}
