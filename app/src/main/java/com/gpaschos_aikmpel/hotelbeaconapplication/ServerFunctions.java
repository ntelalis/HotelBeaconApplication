package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gpaschos on 28/02/18.
 */

public class ServerFunctions {
    // build and return the StringRequest for the login()
    public static StringRequest getLoginRequest(final Context context, final String email, final String pass) {
        return new StringRequest(Request.Method.POST, GlobalVars.loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int name = jsonObject.getInt("successful");
                    if (name == 1) {
                        int customerId = jsonObject.getInt("customerID");
                        Toast.makeText(context, "Login Successful! CustomerID: " + customerId, Toast.LENGTH_SHORT).show();
                    } else if (name == 0) {
                        Toast.makeText(context, String.valueOf(name), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };

    }

    // build and return the StringRequest for register()
    public static StringRequest getRegisterRequest(final Context context, final String email, final String pass,
                                                 final String firstName, final String lastName, final String title){
        return new StringRequest(Request.Method.POST, GlobalVars.registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int successful = jsonObject.getInt("successful");
                    if (successful == 1) {
                        Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                    else if (successful == 0) {
                        Toast.makeText(context, "There was a problem", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", pass);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("title", title);
                return params;
            }
        };
    }
    //build and return the StringRequest for loadSpinnerData()
    public static StringRequest getSpinnerDataRequest(final Context context, String url, final Spinner spinner) {
        //RequestQueue requestQueue=Volley.newRequestQueue(context);
        final ArrayList<String> titleList = new ArrayList<>();
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("successful") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("titleList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            //String country = jsonObject1.getString("Country");
                            String title = jsonArray.getString(i);
                            titleList.add(title);
                        }
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, titleList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });}


    public StringRequest forgotPassRequest(final Context context, final String email) {
        return new StringRequest(Request.Method.POST, GlobalVars.forgotUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        Intent intent = new Intent(context,ForgotVerifyActivity.class);
                        context.startActivity(intent);
                    } else if (success == 0) {
                        Toast.makeText(context, "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
    }

    // build and return the StringRequest for the forgot()
    public static StringRequest getForgotRequest(final Context context, final String email) {
        StringRequest request = new StringRequest(Request.Method.POST, GlobalVars.forgotUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aa",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("successful");
                    if (success == 1) {
                        Intent intent = new Intent(context,ForgotVerifyActivity.class);
                        intent.putExtra("email",email);
                        context.startActivity(intent);
                    } else if (success == 0) {
                        Toast.makeText(context, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
         //Policy for timeout
        request.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return request;
    }

    public static StringRequest getForgotVerifyRequest(final Context context,final String email, final String code) {
        StringRequest request = new StringRequest(Request.Method.POST, GlobalVars.forgotVerifyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("successful");
                    if (success == 1) {
                        Intent intent = new Intent(context,ForgotNewPassActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("code",code);
                        context.startActivity(intent);
                    } else if (success == 0) {
                        Toast.makeText(context, "Wrong Verification", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("verification", code);
                return params;
            }
        };
        return request;
    }

    public static StringRequest getForgotNewPassRequest(final Context context, final String pass, final String email, final String code) {
        StringRequest request = new StringRequest(Request.Method.POST, GlobalVars.forgotNewPassUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("successful");
                    if (success == 1) {
                        Intent intent = new Intent(context,LoginActivity.class);
                        context.startActivity(intent);
                    } else if (success == 0) {
                        Toast.makeText(context, "Something Gone Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pass", pass);
                params.put("email",email);
                params.put("code",code);
                return params;
            }
        };
        return request;
    }

}
