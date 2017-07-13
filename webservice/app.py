from flask import Flask, jsonify
app = Flask(__name__)

features = [
    {
        'name': 'FEATURE_BLA',
        'done': False
    },
    {
        'name': 'FEATURE_SHOP',
        'done': True
    }
]

@app.route('/tutorialclicker/api/v1.0/features', methods=['GET'])
def get_features():
    return jsonify({'features': features})

if __name__ == '__main__':
    app.run(debug=True)